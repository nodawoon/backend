package nodawoon.me_to_you.domain.user.presentation.dto.response;

public record SearchNicknameResponse(
        String nickname, String profileImageUrl
) {
    public SearchNicknameResponse(Object[] result) {
        this((String) result[0], (String) result[1]);
    }
}
