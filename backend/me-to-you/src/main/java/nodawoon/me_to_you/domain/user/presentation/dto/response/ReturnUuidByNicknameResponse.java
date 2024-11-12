package nodawoon.me_to_you.domain.user.presentation.dto.response;

import nodawoon.me_to_you.domain.user.domain.User;

public record ReturnUuidByNicknameResponse(
        String shareUrl
) {
    public ReturnUuidByNicknameResponse(User user) {
        this(user.getShareUrl());
    }
}
