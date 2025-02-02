package LittlePet.UMC.domain.enums;

public enum MediaTypeEnum {
    Text, Picture; //확장자를 넣는게 맞나?

    public static MediaTypeEnum WhatMediaType(String media) {
        switch (media) {
            case "text","Text","TEXT":
                return MediaTypeEnum.Text;
            case "picture", "Picture", "PICTURE":
            default:
                return MediaTypeEnum.Picture; //default에서는 에러해야함
        }
    }
}
