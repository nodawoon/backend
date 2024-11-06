package nodawoon.me_to_you.domain.user.presentation.dto.response;

import nodawoon.me_to_you.domain.user.domain.User;

public record ReturnNicknameByUUIDResponse(
        String nickname
) {
    public ReturnNicknameByUUIDResponse(User user){
        this(user.getNickname());
    }
}
