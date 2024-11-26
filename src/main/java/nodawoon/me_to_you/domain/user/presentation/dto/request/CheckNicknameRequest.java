package nodawoon.me_to_you.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckNicknameRequest(
        @NotBlank
        @Size(min = 2, max = 8, message = "닉네임은 2자 이상 8자 이하여야 합니다.")
        String nickname
) {
}
