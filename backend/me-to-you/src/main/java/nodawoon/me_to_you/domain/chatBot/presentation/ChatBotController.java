package nodawoon.me_to_you.domain.chatBot.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nodawoon.me_to_you.domain.chatBot.presentation.dto.request.ChangeChatBotAnswerRequest;
import nodawoon.me_to_you.domain.chatBot.presentation.dto.request.CreateChatBotRequest;
import nodawoon.me_to_you.domain.chatBot.presentation.dto.response.ChatBotResponse;
import nodawoon.me_to_you.domain.chatBot.service.ChatBotService;
import nodawoon.me_to_you.domain.user.presentation.dto.response.UserProfileResponse;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;


@Tag(name = "챗봇 질문", description = "챗봇 질문 관련 API")
@RestController
@RequestMapping("/api/chatbots")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

    @Operation(summary = "챗봇 질문하기")
    @PostMapping("/{targetUserId}")
    public ChatBotResponse createChatBot(@PathVariable Long targetUserId, @RequestBody CreateChatBotRequest createChatBotRequest) {
        return chatBotService.createChatBot(targetUserId, createChatBotRequest);
    }

    @Operation(summary = "내가 참여한 채팅방 목록 조회하기")
    @GetMapping("/chat-room")
    public Slice<UserProfileResponse> getMyChatRoomList(@RequestParam int page) {
        return chatBotService.getMyChatRoomList(page);
    }

    @Operation(summary = "해당 질문 gpt에 다시 물어보기")
    @PatchMapping("/{chatBotId}/request")
    public ChatBotResponse requestChatBot(@PathVariable Long chatBotId) {
        return chatBotService.requestChatBot(chatBotId);
    }

    @Operation(summary = "내가 질문 받는 사람에게 한 모든 질문 조회하기")
    @GetMapping("/{targetUserId}")
    public Slice<ChatBotResponse> getAllChatBots(@PathVariable Long targetUserId, @RequestParam int page) {
        return chatBotService.getAllChatBots(targetUserId, page);
    }

    @Operation(summary = "질문 받는 사람이 자신에게 한 모든 질의응답 중 챗봇이 답변한 질문 조회하기")
    @GetMapping()
    public Slice<ChatBotResponse> getChatBotMyList(@RequestParam String status, int page) {
        return chatBotService.getChatBotMyList(status, page);
    }

    @Operation(summary = "챗봇이 답변하지 못한 질문 답변 수정하기")
    @PatchMapping("/{chatBotId}/response")
    public ChatBotResponse updateChatBotAnswer(@PathVariable Long chatBotId, @RequestBody ChangeChatBotAnswerRequest changeChatBotAnswerRequest) {
        return chatBotService.updateChatBotAnswer(chatBotId, changeChatBotAnswerRequest);
    }

    @Operation(summary = "프롬프트에 추가할 질문 추가하기")
    @PatchMapping("/{chatBotId}/prompt/add")
    public ChatBotResponse addChatBotPrompt(@PathVariable Long chatBotId) {
        return chatBotService.addChatBotPrompt(chatBotId);
    }

    @Operation(summary = "프롬프트에 추가할 질문 빼기")
    @PatchMapping("/{chatBotId}/prompt/remove")
    public ChatBotResponse removeChatBotPrompt(@PathVariable Long chatBotId) {
        return chatBotService.removeChatBotPrompt(chatBotId);
    }

    @Operation(summary = "내가 한 질문 삭제")
    @DeleteMapping("/{chatBotId}/question")
    public void deleteQuestion(@PathVariable Long chatBotId) {
        chatBotService.deleteQuestion(chatBotId);
    }

    @Operation(summary = "나에게 온 질문 삭제")
    @DeleteMapping("/{chatBotId}/my-question")
    public void deleteMyQuestion(@PathVariable Long chatBotId) {
        chatBotService.deleteMyQuestion(chatBotId);
    }
}
