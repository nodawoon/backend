package nodawoon.me_to_you.domain.chatBot.presentation.dto.response;

import nodawoon.me_to_you.domain.chatBot.domain.AnswerStatus;

public record TargetUserWithLastChatBotResponse(
        Long targetUserId,
        Long chatBotId,
        String nickname,
        String profileImageUrl,
        String lastChatBotAnswer,
        AnswerStatus answerStatus,
        boolean isNew
) {
}