package com.b101.recruit.reponse;

import org.springframework.data.domain.Page;

import com.b101.common.model.response.BaseResponseBody;
import com.b101.recruit.domain.dto.VerificationDto;
import com.b101.recruit.domain.entity.Gallery;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("VerificationResponse")
public class VerificationRes extends BaseResponseBody{
	
	private VerificationDto verificationDto;
	
	public static VerificationRes of(Integer statusCode, String message,VerificationDto verificationDto) {
		VerificationRes res = new VerificationRes();
		res.setVerificationDto(verificationDto);
		res.setMessage(message);
		res.setStatusCode(statusCode);
		return res;
	}

}
