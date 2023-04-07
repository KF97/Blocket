package com.b101.recruit.reponse;

import com.b101.common.model.response.BaseResponseBody;
import com.b101.recruit.domain.entity.UserWallet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *  지갑 정보를 담은 response
 */

@Getter
@Setter
@ApiModel("UserWalletResponse")
public class UserWalletRes extends BaseResponseBody {
    @ApiModelProperty(name = "공개 키 주소")
    String address;
    @ApiModelProperty(name = "잔고")
    String balance;
    @ApiModelProperty(name = "트랜잭션 전송 횟수")
    String receiving_count;

    public static UserWalletRes of(Integer statusCode, UserWallet userWallet) {
        UserWalletRes res = new UserWalletRes();
        res.setAddress(userWallet.getAddress());
        res.setBalance(userWallet.getBalance());
        res.setReceiving_count(userWallet.getReceiving_count());
        return res;
    }
}
