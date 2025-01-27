package LittlePet.UMC.apiPayload.exception.handler;

import LittlePet.UMC.apiPayload.code.BaseErrorCode;
import LittlePet.UMC.apiPayload.exception.GeneralException;

public class PetCategoryHandler extends GeneralException {
    public PetCategoryHandler(BaseErrorCode code) {
        super(code);
    }
}
