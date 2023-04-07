package com.b101.recruit.reponse;

import com.b101.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserWalletUpdatePatchResponse")
public class UserWalletUpdatePatchRes extends BaseResponseBody {

    public static UserWalletUpdatePatchRes of(Integer statusCode, String message) {
        UserWalletUpdatePatchRes res = new UserWalletUpdatePatchRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        return res;
    }
}
