package LittlePet.UMC.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HealthStatusEnum {
    LOSS_OF_APPETITE("LOSS_OF_APPETITE", "기력 저하"),
    MOUTH_ODOR("MOUTH_ODOR", "구토"),
    COUGH("COUGH", "기침"),
    ABNORMAL_BEHAVIOR("ABNORMAL_BEHAVIOR", "이상 행동"),
    HAIR_LOSS("HAIR_LOSS", "털 빠짐"),
    DECREASED_BODY_TEMP("DECREASED_BODY_TEMP", "체온 저하"),
    INCREASED_BODY_TEMP("INCREASED_BODY_TEMP", "체온 상승"),
    DISCHARGE("DISCHARGE", "분비물"),
    OTHER("OTHER", "기타");

    private final String code;  // 영문 코드
    private final String description; // 한글 설명

    /**
     * 코드로 Enum 찾기
     */
    public static HealthStatusEnum fromCode(String code) {
        for (HealthStatusEnum value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown HealthStatusEnum code: " + code);
    }
}