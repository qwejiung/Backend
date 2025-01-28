package LittlePet.UMC.domain.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HealthStatusEnum {

    DETERIORATED("DETERIORATED","악화"),
    FAIR("FAIR","양호"),
    HEALTHY("HEALTHY","건강");

    private final String code;        // 영문 코드
    private final String description; // 한글 설명

    public static HealthStatusEnum fromCode(String code) {
        for (HealthStatusEnum value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown HealthStatusEnum code: " + code);
    }

    public static HealthStatusEnum fromDescription(String description) {
        for (HealthStatusEnum value : values()) {
            if (value.description.equals(description)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown HealthStatusEnum description: " + description);
    }
}