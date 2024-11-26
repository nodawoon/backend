package nodawoon.me_to_you.domain.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    MAN("남성"),
    WOMAN("여성");

    private final String value;
}
