package LittlePet.UMC.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AtypicalSymptomEnum {
    LOSS_OF_APPETITE("LOSS_OF_APPETITE", "기력 저하"),
    MOUTH_ODOR("MOUTH_ODOR", "구토"),      // (주의: MOUTH_ODOR = '입 냄새'가 일반적이나, 코드상 '구토'로 되어 있음)
    COUGH("COUGH", "기침"),
    ABNORMAL_BEHAVIOR("ABNORMAL_BEHAVIOR", "이상 행동"),
    HAIR_LOSS("HAIR_LOSS", "털 빠짐"),
    DECREASED_BODY_TEMP("DECREASED_BODY_TEMP", "체온 저하"),
    INCREASED_BODY_TEMP("INCREASED_BODY_TEMP", "체온 상승"),
    DISCHARGE("DISCHARGE", "분비물"),
    OTHER("OTHER", "기타");

    private final String code;        // 영문 코드
    private final String description; // 한글 설명

    /**
     * 영문 코드로 Enum 찾기
     */
    public static AtypicalSymptomEnum fromCode(String code) {
        for (AtypicalSymptomEnum value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown AtypicalSymptomEnum code: " + code);
    }

    /**
     * 한글 설명으로 Enum 찾기
     */
    public static AtypicalSymptomEnum fromDescription(String description) {
        for (AtypicalSymptomEnum value : values()) {
            if (value.description.equals(description)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown AtypicalSymptomEnum description: " + description);
    }
}
