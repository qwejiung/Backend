package LittlePet.UMC.apiPayload.exception.handler;

import LittlePet.UMC.apiPayload.code.BaseErrorCode;
import LittlePet.UMC.apiPayload.exception.GeneralException;

public class HospitalHandler extends GeneralException {

    public HospitalHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public String getMessage() {
        return this.getErrorReason().getMessage();
    }
}
