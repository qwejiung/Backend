package LittlePet.UMC.apiPayload.exception.handler;

import LittlePet.UMC.apiPayload.code.BaseErrorCode;
import LittlePet.UMC.apiPayload.exception.GeneralException;

public class BadgeHandler extends GeneralException {
    public BadgeHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
