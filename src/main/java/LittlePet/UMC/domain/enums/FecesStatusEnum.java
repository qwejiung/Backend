package LittlePet.UMC.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FecesStatusEnum {
    NORMAL("NORMAL","적당한 무르기"),
    HARD("HARD","딱딱한 똥"),
    DIARRHEA("DIARRHEA","설사"),
    BLOODY("BLOODY","혈변"),
    NOT_DEFECATED("NOT_DEFECATED","대변 안 봄");

    private final String code;        // 영문 코드
    private final String description; // 한글 설명

    public static FecesStatusEnum fromCode(String code) {
        for (FecesStatusEnum value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown FecesStatusEnum code: " + code);
    }

    public static FecesStatusEnum fromDescription(String description) {
        for (FecesStatusEnum value : values()) {
            if (value.description.equals(description)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown FecesStatusEnum description: " + description);
    }
}
