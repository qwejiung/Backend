package LittlePet.UMC.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FecesColorStatusEnum {
    BROWN("BROWN", "갈색"),
    BLACK("BLACK", "검은색"),
    RED("RED", "붉은색"),
    YELLOW("YELLOW", "누런색"),
    GREEN("GREEN", "초록색"),
    GRAY("GRAY", "회색");

    private final String code;  // 영문 코드
    private final String description; // 한글 설명

    /**
     * 코드로 Enum 찾기
     */
    public static FecesColorStatusEnum fromCode(String code) {
        for (FecesColorStatusEnum value : values()) {
            if (value.code.equalsIgnoreCase(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown FecesColorStatusEnum code: " + code);
    }
}
