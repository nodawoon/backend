package nodawoon.me_to_you.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;
import nodawoon.me_to_you.domain.user.domain.Gender;
import nodawoon.me_to_you.domain.user.domain.Mbti;

import java.time.LocalDate;

public record SignUpUserRequest(
        @NotBlank
        String nickname,

        @NotBlank
        String email,

        Gender gender,

        @PastOrPresent(message = "미래의 날짜는 선택하지 못합니다.")
        LocalDate birthday,

        @NotBlank
        String profileImage,

        Mbti mbti,

        OauthServerType oauthServerType
) {
}