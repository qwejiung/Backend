package LittlePet.UMC.domain.enums;

public enum SocialProviderEnum {
//    NAVER,
//    KAKAO,
//    GOOGLE,
//    OTHER;

    NAVER("naver"),
    GOOGLE("google");

    private final String provider;

    SocialProviderEnum(String provider) {
        this.provider = provider;
    }

    public static SocialProviderEnum fromString(String provider) {
        for (SocialProviderEnum value : SocialProviderEnum.values()) {
            if (value.provider.equalsIgnoreCase(provider)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown provider: " + provider);
    }
}
