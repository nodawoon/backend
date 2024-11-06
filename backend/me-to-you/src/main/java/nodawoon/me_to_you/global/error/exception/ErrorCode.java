package nodawoon.me_to_you.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    FILE_SIZE(400, "업로드 된 파일 사이즈가 초과되었습니다."),
    BAD_FILE_EXTENSION(400, "유효한 확장명의 파일이 아닙니다."),
    FILE_UPLOAD_FAIL(400, "파일 업로드에 실패하였습니다."),
    BAD_REQUEST(400,"잘못된 입력입니다."),
    MISSING_PARAMS(400,"필수 파라미터가 누락되었습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    INVALID_REFRESH_TOKEN(401, "리프레시 토큰이 유효하지 않습니다"),
    INVALID_ACCESS_TOKEN(401, "Access 토큰이 유효하지 않습니다"),
    INVALID_URL(401, "URL 주소가 유요하지 않습니다."),

    /* 403 */
    REGISTER_EXPIRED_TOKEN(403,"만료된 리프레쉬 토큰입니다."),
    USER_INFO_NOT_FOUND(404, "회원가입을 진행해 주세요."),
    USER_NOT_SELF_SURVEY_HOST(403, "해당하는 30문 30답의 소유자가 아닙니다."),
    USER_NOT_CHATBOT_HOST(403, "해당하는 챗봇 질문의 소유자가 아닙니다."),
    RESPONDENT_NOT_FOUND(403, "해당하는 응답자를 찾을 수 없습니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(404, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    SELF_SURVEY_NOT_FOUND(404, "해당하는 정보의 30문 30답을 찾을 수 없습니다."),
    CHATBOT_NOT_FOUND(404, "해당하는 정보의 챗봇 질문을 찾을 수 없습니다."),
    NO_ERROR_TYPE(404, "오류 발생"),
    OAUTH_MEMBER_NOT_FOUND(404, "해당하는 로그인 정보를 찾을 수 없습니다."),
    FILE_EMPTY(404, "업로드 된 파일을 찾을 수 없습니다."),

    /* 409 중복된 리소스 */
    USER_DUPLICATION(409, "이미 가입된 사용자입니다."),
    NICKNAME_DUPLICATION(409, "이미 사용중인 닉네임입니다."),

    /* 429 요청 횟수 초과 */

    /* 500 SERVER_ERROR */
    INTERNAL_SERVER_ERROR(500,"서버 에러"),
    IMAGE_PROCESSING(500, "이미지 처리 중 오류가 발생했습니다."),
    API_JSON_PARSE_ERROR(500, "API JSON 응답에 파싱 에러가 발생했습니다.");

    private int status;
    private String reason;
}
