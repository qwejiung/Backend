package LittlePet.UMC.apiPayload.exception.handler;

import LittlePet.UMC.apiPayload.code.BaseErrorCode;
import LittlePet.UMC.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
