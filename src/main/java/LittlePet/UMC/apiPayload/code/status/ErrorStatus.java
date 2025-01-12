package LittlePet.UMC.apiPayload.code.status;

import LittlePet.UMC.apiPayload.code.BaseErrorCode;
import LittlePet.UMC.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _NULL_VALUE(HttpStatus.BAD_REQUEST, "COMMON4001", "필수 값이 누락되었습니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "접근 권한이 없습니다."),

    //JWT token error
    TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH4011", "토큰이 손상되었거나 유효하지 않은 값입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH4012", "토큰이 만료되어 인증이 필요합니다."),
    TOKEN_BAD_REQUEST(HttpStatus.BAD_REQUEST, "AUTH4001", "잘못된 토큰 형식입니다."),

    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4001", "존재하지 않는 사용자입니다."),
    SOCIAL_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"MEMBER4003","소셜 로그인 토큰이 유효하지 않습니다."),

    // 커뮤니티 관련 에러
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "존재하지 않는 게시물입니다."),
    ARTICLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "ARTICLE4004", "게시물에 접근할 권한이 없습니다."),
    ARTICLE_ATTACHMENT_TOO_LARGE(HttpStatus.BAD_REQUEST, "ATTACHMENT4001", "첨부파일 크기가 허용된 한도를 초과했습니다."),
    ARTICLE_UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "ATTACHMENT4002", "지원되지 않는 파일 형식입니다."),

    // 댓글 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT4001", "존재하지 않는 댓글입니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMENT4003", "댓글에 대한 권한이 없습니다."),

    //반려동물 관련 에러
    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "PET4001", "존재하지 않는 반려동물입니다."),

    //카테고리 관련 에러
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "CATEGORY4002", "유효하지 않은 카테고리입니다."),

    // test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
