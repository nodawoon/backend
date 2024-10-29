package nodawoon.me_to_you.domain.user.presentation.dto.response;

import nodawoon.me_to_you.domain.oauth.domain.OauthServerType;
import nodawoon.me_to_you.domain.user.domain.Gender;
import nodawoon.me_to_you.domain.user.domain.Mbti;
import nodawoon.me_to_you.domain.user.domain.User;

import java.time.LocalDate;

public record UserProfileResponse(
    Long userId,
    String email,
    String nickname,
    Gender gender,
    LocalDate birthday,
    Mbti mbti,
    String profileImage,
    OauthServerType oauthServerType
) {
    public UserProfileResponse(User user) {
        this(user.getId(), user.getEmail(), user.getNickname(), user.getGender(), user.getBirthday(),
                user.getMbti(), user.getProfileImageUrl(), user.getOauthServerType());
    }
}
