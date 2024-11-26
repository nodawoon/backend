package nodawoon.me_to_you.domain.chatBot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nodawoon.me_to_you.domain.chatBot.domain.AnswerStatus;
import nodawoon.me_to_you.domain.chatBot.domain.ChatBot;
import nodawoon.me_to_you.domain.chatBot.domain.repository.ChatBotRepository;
import nodawoon.me_to_you.domain.chatBot.exception.ChatBotNotFoundException;
import nodawoon.me_to_you.domain.chatBot.presentation.dto.request.ChangeChatBotAnswerRequest;
import nodawoon.me_to_you.domain.chatBot.presentation.dto.request.CreateChatBotRequest;
import nodawoon.me_to_you.domain.chatBot.presentation.dto.response.ChatBotResponse;
import nodawoon.me_to_you.domain.chatBot.presentation.dto.response.TargetUserWithLastChatBotResponse;
import nodawoon.me_to_you.domain.selfSurvey.service.SelfSurveyServiceUtils;
import nodawoon.me_to_you.domain.user.domain.User;
import nodawoon.me_to_you.domain.user.presentation.dto.response.UserProfileResponse;
import nodawoon.me_to_you.global.chatGpt.service.ChatGptServiceUtils;
import nodawoon.me_to_you.global.utils.user.UserUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.stream.Collectors;

import static nodawoon.me_to_you.domain.chatBot.domain.AnswerStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatBotService {

    private final ChatBotRepository chatBotRepository;
    private final UserUtils userUtils;
    private final ChatGptServiceUtils chatGptServiceUtils;
    private final SelfSurveyServiceUtils selfSurveyServiceUtils;

    // 챗봇 질문하기
    @Transactional
    public ChatBotResponse createChatBot(Long targetUserId, CreateChatBotRequest createChatBotRequest) {
        User questioner = userUtils.getUserFromSecurityContext();
        User targetUser = userUtils.getUserById(targetUserId);
        String selfSurveyPrompt = formatSelfSurveys(targetUser);
        String chatBotPrompt = formatChatBots(targetUser);
        String userProfilePrompt = formatUserProfile(targetUser);
        String answer = chatGptServiceUtils.generateChatgpt(selfSurveyPrompt, chatBotPrompt, userProfilePrompt, createChatBotRequest.question());
        AnswerStatus answerStatus;

        if (!answer.contains("잘 모르겠어")) {
            answerStatus = ANSWERED_BY_BOT;
        } else {
            answerStatus = NONE;
        }

        ChatBot chatBot = ChatBot.createChatBot(questioner, targetUser, createChatBotRequest.question(), answer, answerStatus);

        chatBotRepository.save(chatBot);

        return new ChatBotResponse(chatBot, new UserProfileResponse(questioner));
    }

    // 내가 참여한 채팅방 목록 조회하기
    public Slice<Object[]> getMyChatRoomList(int page) {
        User user = userUtils.getUserFromSecurityContext();
        Pageable pageable = PageRequest.of(page, 10);

        Slice<Object[]> chatBotList = chatBotRepository.findDistinctTargetUsersWithLastChatBot(user, pageable);

        return chatBotList;
    }

    // 해당 질문 gpt에 다시 물어보기
    @Transactional
    public ChatBotResponse requestChatBot(Long chatBotId) {
        User questioner = userUtils.getUserFromSecurityContext();
        ChatBot chatBot = queryChatBot(chatBotId);

        chatBot.validQuestionUserHost(questioner);
        chatBot.requestChatBot();

        User targetUser = chatBot.getTargetUser();
        String selfSurveyPrompt = formatSelfSurveys(targetUser);
        String chatBotPrompt = formatChatBots(targetUser);
        String userProfilePrompt = formatUserProfile(targetUser);
        String answer = chatGptServiceUtils.generateChatgpt(selfSurveyPrompt, chatBotPrompt, userProfilePrompt, chatBot.getAnswer());
        AnswerStatus answerStatus;

        chatBot.subLimitCount();

        if (!answer.contains("잘 모르겠어")) {
            chatBot.updateAnswerStatus(ANSWERED_BY_BOT);
            chatBot.updateAnswer(answer);
        }

        return new ChatBotResponse(chatBot, new UserProfileResponse(questioner));
    }

    // 기다리기
    @Transactional
    public ChatBotResponse waitChatBot(Long chatBotId) {
        User questioner = userUtils.getUserFromSecurityContext();
        ChatBot chatBot = queryChatBot(chatBotId);

        chatBot.validQuestionUserHost(questioner);
        chatBot.updateAnswerStatus(UNANSWERED_BY_BOT);

        return new ChatBotResponse(chatBot, new UserProfileResponse(questioner));
    }

    // 챗봇이 답변하지 못한 질문 삭제하기
    @Transactional
    public void deleteQuestion(Long chatBotId) {
        User user = userUtils.getUserFromSecurityContext();
        ChatBot chatBot = queryChatBot(chatBotId);

        chatBot.validQuestionUserHost(user);

        chatBotRepository.delete(chatBot);
    }

    // 자신에게 온 질문 삭제하기
    @Transactional
    public void deleteMyQuestion(Long chatBotId) {
        User user = userUtils.getUserFromSecurityContext();
        ChatBot chatBot = queryChatBot(chatBotId);

        chatBot.validTargetUserHost(user);

        chatBotRepository.delete(chatBot);
    }

    // 내가 질문 받는 사람에게 한 모든 질문 조회하기
    public Slice<ChatBotResponse> getAllChatBots(Long targetUserId, int page) {
        User questioner = userUtils.getUserFromSecurityContext();
        User targetUser = userUtils.getUserById(targetUserId);
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Slice<ChatBot> chatBotSlice = chatBotRepository.findAllByTargetUserAndQuestionUser(targetUser, questioner, pageable);

        return chatBotSlice.map(c -> new ChatBotResponse(c, new UserProfileResponse(c.getQuestionUser())));
    }

    // 질문 받는 사람이 자신에게 한 모든 질의응답 중 질문 상태에 맞게 조회하기
    public Slice<ChatBotResponse> getChatBotMyList(String status, int page) {
        User user = userUtils.getUserFromSecurityContext();
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "id"));
        Slice<ChatBot> chatBotSlice;

        if (status.equals("answer-bot")) {
            chatBotSlice = chatBotRepository.findAllByTargetUserAndAnswerStatus(user, ANSWERED_BY_BOT, pageable);
        } else if (status.equals("unanswer-bot")) {
            chatBotSlice = chatBotRepository.findAllByTargetUserAndAnswerStatus(user, UNANSWERED_BY_BOT, pageable);
        } else if (status.equals("answer-user")) {
            chatBotSlice = chatBotRepository.findAllByTargetUserAndAnswerStatus(user, ANSWERED_BY_USER, pageable);
        } else {
            return new SliceImpl<>(Collections.emptyList(), pageable, false);
        }

        return chatBotSlice.map(c -> new ChatBotResponse(c, new UserProfileResponse(c.getQuestionUser())));
    }

    // 답변 수정하기
    @Transactional
    public ChatBotResponse updateChatBotAnswer(Long chatBotId, ChangeChatBotAnswerRequest changeChatBotAnswerRequest) {
        User user = userUtils.getUserFromSecurityContext();
        ChatBot chatBot = queryChatBot(chatBotId);

        chatBot.validTargetUserHost(user);

        chatBot.userAnswer(changeChatBotAnswerRequest.answer());

        return new ChatBotResponse(chatBot, new UserProfileResponse(chatBot.getQuestionUser()));
    }

    // 프롬프트에 추가할 질문 추가하기
    @Transactional
    public ChatBotResponse addChatBotPrompt(Long chatBotId) {
        User user = userUtils.getUserFromSecurityContext();
        ChatBot chatBot = queryChatBot(chatBotId);

        chatBot.validTargetUserHost(user);

        chatBot.addChatBotPrompt();

        return new ChatBotResponse(chatBot, new UserProfileResponse(chatBot.getQuestionUser()));
    }
    
    // 프롬프트에 추가할 질문 빼기
    @Transactional
    public ChatBotResponse removeChatBotPrompt(Long chatBotId) {
        User user = userUtils.getUserFromSecurityContext();
        ChatBot chatBot = queryChatBot(chatBotId);

        chatBot.validTargetUserHost(user);

        chatBot.removeChatBotPrompt();

        return new ChatBotResponse(chatBot, new UserProfileResponse(chatBot.getQuestionUser()));
    }

    // 읽음 상태로 변경
    @Transactional
    public void readChatBot(Long chatBotId) {
        User user = userUtils.getUserFromSecurityContext();
        ChatBot chatBot = queryChatBot(chatBotId);

        chatBot.validQuestionUserHost(user);
        chatBot.readChatBot();
    }

    // 30문 30답 조회 후 프롬프트 생성
    public String formatSelfSurveys(User targetUser) {
        return selfSurveyServiceUtils.getSelfSurveysByUser(targetUser).stream()
                .map(survey -> survey.getQuestion() + " : " + survey.getAnswer())
                .collect(Collectors.joining("\n"));
    }

    // 챗봇 질문 조회 후 프롬프트 생성
    public String formatChatBots(User targetUser) {
        return chatBotRepository.findByTargetUserAndIsQuestionIncludedTrue(targetUser).stream()
                .map(survey -> survey.getQuestion() + " : " + survey.getAnswer())
                .collect(Collectors.joining("\n"));
    }

    // 질문 받는 사용자의 정보 조회 후 프롬프트 생성
    public String formatUserProfile(User targetUser) {
        return "생년월일? : " + targetUser.getBirthday() + "\n"
                + "MBTI는? : " + targetUser.getMbti();
    }

    public ChatBot queryChatBot(Long chatBotId) {
        return chatBotRepository.findById(chatBotId).orElseThrow(() -> ChatBotNotFoundException.EXCEPTION);
    }
}
