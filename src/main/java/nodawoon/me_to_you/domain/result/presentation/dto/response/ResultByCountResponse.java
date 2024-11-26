package nodawoon.me_to_you.domain.result.presentation.dto.response;

public record ResultByCountResponse(
        String responseDetail,
        Long count
) {
    public ResultByCountResponse(Object[] result) {
        this(
                (String) result[0],
                (Long) result[1]);
    }
}
