package LittlePet.UMC.apiPayload.exception.handler;

import LittlePet.UMC.apiPayload.code.BaseErrorCode;
import LittlePet.UMC.apiPayload.exception.GeneralException;

public class PetBigCategoryHandler extends GeneralException {
    public PetBigCategoryHandler(BaseErrorCode code) {
        super(code);
    }
}
