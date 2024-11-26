package nodawoon.me_to_you.domain.chatBot.presentation.dto.response;

import nodawoon.me_to_you.domain.chatBot.domain.AnswerStatus;
import nodawoon.me_to_you.domain.chatBot.domain.ChatBot;
import nodawoon.me_to_you.domain.user.presentation.dto.response.UserProfileResponse;

import java.time.LocalDateTime;

public record ChatBotResponse(
        Long chatBotId,
        String question,
        String response,
        boolean isQuestionIncluded,
        int limitCount,
        AnswerStatus answerStatus,
        LocalDateTime createdDate,
        LocalDateTime lastModifyDate,
        UserProfileResponse questionerProfile
) {
    public ChatBotResponse(ChatBot chatBot, UserProfileResponse userProfileResponse) {
        this(chatBot.getId(), chatBot.getQuestion(), chatBot.getAnswer(), chatBot.isQuestionIncluded(), chatBot.getLimitCount(), chatBot.getAnswerStatus(),
                chatBot.getCreatedDate(), chatBot.getLastModifyDate(), userProfileResponse);
    }
}
