package com.b101.recruit.reponse;


import com.b101.common.model.response.BaseResponseBody;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserUpdatePatchResponse")
public class UserUpdatePatchRes extends BaseResponseBody{
	
	public static UserUpdatePatchRes of(Integer statusCode, String message) {
		UserUpdatePatchRes res = new UserUpdatePatchRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		return res;
	}

}
