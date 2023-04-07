package com.b101.recruit.reponse;


import com.b101.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserLoginPostResponse")
public class UserLoginPostRes extends BaseResponseBody{
	
	@ApiModelProperty(name = "JWT 인증 토큰", example = "vzahKZZzcXjljc4xJ4oNidi7iB4JIxxQNldg.oyzwDYEf8XbOvGo...")
	String accessToken;
	int type;

	public static UserLoginPostRes of(Integer statusCode, String message, String accessToken,int type) {
		UserLoginPostRes res = new UserLoginPostRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setAccessToken(accessToken);
		res.setType(type);
		return res;
	}
	

}
