package nodawoon.me_to_you.domain.chatBot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnswerStatus {
    ANSWERED_BY_BOT("챗봇이 답변함"),
    UNANSWERED_BY_BOT("챗봇이 답변하지 못함"),
    ANSWERED_BY_USER("사용자가 답변함");

    private final String description;
}
