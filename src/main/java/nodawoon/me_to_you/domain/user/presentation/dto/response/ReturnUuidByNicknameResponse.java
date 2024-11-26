package nodawoon.me_to_you.domain.user.presentation.dto.response;

import nodawoon.me_to_you.domain.user.domain.User;

public record ReturnUuidByNicknameResponse(
        Long userId, String nickname, String profileImageUrl
) {
    public ReturnUuidByNicknameResponse(User user) {
        this(user.getId(), user.getNickname(), user.getProfileImageUrl());
    }
}
