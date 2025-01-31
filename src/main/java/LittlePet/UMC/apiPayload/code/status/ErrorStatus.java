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
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON4001","잘못된 요청입니다."),
    _NULL_VALUE(HttpStatus.BAD_REQUEST, "COMMON4002", "필수 값이 누락되었습니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON4011","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON4031", "접근 권한이 없습니다."),

    HOSPITAL_VISIT_ERROR(HttpStatus.BAD_REQUEST, "HOSPITAL4001", "병원 내진 여부가 true일 경우 진단명과 처방 내역은 필수입니다."),
    HOSPITAL_VISIT_NULL_ERROR(HttpStatus.BAD_REQUEST, "HOSPITAL4002", "병원 내진 여부가 false일 경우 진단명과 처방 내역을 입력할 수 없습니다."),


    // JWT token error
    TOKEN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH4011", "토큰이 손상되었거나 유효하지 않은 값입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH4012", "토큰이 만료되어 인증이 필요합니다."),
    TOKEN_BAD_REQUEST(HttpStatus.BAD_REQUEST, "AUTH4001", "잘못된 토큰 형식입니다."),



    // 멤버 관련 에러
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4041", "존재하지 않는 사용자입니다."),
    SOCIAL_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND,"MEMBER4042","소셜 로그인 토큰이 유효하지 않습니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "USER4091", "중복된 닉네임입니다."),

    // 커뮤니티 관련 에러
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4041", "존재하지 않는 게시물입니다."),
    ARTICLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "ARTICLE4031", "게시물에 접근할 권한이 없습니다."),
    ARTICLE_ATTACHMENT_TOO_LARGE(HttpStatus.BAD_REQUEST, "ATTACHMENT4002", "첨부파일 크기가 허용된 한도를 초과했습니다."),
    ARTICLE_UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "ATTACHMENT4151", "지원되지 않는 파일 형식입니다."),

    // 댓글 관련 에러
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT4041", "존재하지 않는 댓글입니다."),
    COMMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMENT4031", "댓글에 대한 권한이 없습니다."),

    // 반려동물 관련 에러
    PET_NOT_FOUND(HttpStatus.NOT_FOUND, "PET4041", "존재하지 않는 반려동물입니다."),

    // 카테고리 관련 에러
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "CATEGORY4041", "존재하지 않는 카테고리입니다."),
    INVALID_CATEGORY(HttpStatus.BAD_REQUEST, "CATEGORY4003", "유효하지 않은 카테고리입니다."),

    // 테스트용
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4004", "테스트입니다"),

    //유저 관련 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4041","존재하지 않는 유저입니다."),

    //뱃지 타입 관련 에러
    BADGE_NOT_FOUND(HttpStatus.NOT_FOUND, "BADGE4041","존재하지 않는 뱃지입니다. '글쓰기마스터' , '소통천재', '소통응원왕','인기스타' 중 하나를 선택해주세요"),
    BADGE_ALREADY_OWNED(HttpStatus.BAD_REQUEST, "BADGE4001", "이미 해당 뱃지를 보유하고 있습니다. '글쓰기마스터' , '소통천재', '소통응원왕','인기스타' 중 다른 뱃지를 선택해주세요"),
    BADGE_NOT_QUALIFIED(HttpStatus.BAD_REQUEST, "BADGE4002","뱃지를 받을 조건이 충족되지않았습니다.");
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
