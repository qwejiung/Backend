package LittlePet.UMC.apiPayload.exception.handler;

import LittlePet.UMC.apiPayload.code.BaseErrorCode;
import LittlePet.UMC.apiPayload.exception.GeneralException;
import LittlePet.UMC.domain.postEntity.PostCategory;

public class PostCategoryHandler extends GeneralException {
    public PostCategoryHandler(BaseErrorCode code) {
        super(code);
    }

}
