package com.b101.recruit.reponse;

import org.springframework.data.domain.Page;

import com.b101.common.model.response.BaseResponseBody;
import com.b101.recruit.domain.dto.VerificationDto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("VerificationListResponse")
public class VerificationListRes extends BaseResponseBody{
	
	private Page<VerificationDto> verificationList;

	public static VerificationListRes of(Integer statusCode, String message, Page<VerificationDto> verificationList) {
		VerificationListRes res = new VerificationListRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setVerificationList(verificationList);
		return res;
	}
}
