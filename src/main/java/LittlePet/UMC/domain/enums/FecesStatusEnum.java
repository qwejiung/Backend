package LittlePet.UMC.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FecesStatusEnum {
    NORMAL("NORMAL", "정상"),
    DIARRHEA("DIARRHEA", "설사"),
    CONSTIPATION("CONSTIPATION", "변비"),
    HARD("HARD", "단단함"),
    ABNORMAL_COLOR("ABNORMAL_COLOR", "비정상 색상"),
    BLOOD_IN_STOOL("BLOOD_IN_STOOL", "혈변");

    private final String code;  // 영문 코드
    private final String description; // 한글 설명

    /**
     * 코드로 Enum 찾기
     */
    public static FecesStatusEnum fromCode(String code) {
        for (FecesStatusEnum value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown FecesStatusEnum code: " + code);
    }
}
