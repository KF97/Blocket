package com.b101.recruit.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserWalletUpdatePatchRequest")
public class UserWalletUpdatePatchReq {

    @ApiModelProperty(name = "공개 키 주소")
    private String address;
    @ApiModelProperty(name = "잔고")
    private String balance;
    @ApiModelProperty(name = "트랜잭션 전송 횟수")
    private String receiving_count;

}
