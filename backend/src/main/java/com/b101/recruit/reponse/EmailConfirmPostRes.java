package com.b101.recruit.reponse;



import com.b101.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("EmailConfirmPostResponse")
public class EmailConfirmPostRes extends BaseResponseBody{
	@ApiModelProperty(name = "이메일 인증 토큰", example = "")
	String emailToken;

	public static EmailConfirmPostRes of(Integer statusCode, String message, String emailToken) {
		EmailConfirmPostRes res = new EmailConfirmPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setEmailToken(emailToken);
		return res;
	}
}
