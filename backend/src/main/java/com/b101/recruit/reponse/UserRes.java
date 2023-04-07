package com.b101.recruit.reponse;


import com.b101.common.model.response.BaseResponseBody;
import com.b101.recruit.domain.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes extends BaseResponseBody {
	@ApiModelProperty(name = "유저 id")
	long id;
	@ApiModelProperty(name = "유저 Email")
	String email;
	@ApiModelProperty(name = "유저 Name")
	String name;
	@ApiModelProperty(name = "유저 Belong")
	String belong;
	@ApiModelProperty(name = "유저 PhoneNumber")
	String phoneNumber;
	@ApiModelProperty(name = "유저 Brn")
	int brn;
	@ApiModelProperty(name = "유저 Type")
	int type;

	public static UserRes of(Integer statusCode,User user) {
		UserRes res = new UserRes();
		res.setId(user.getId());
		res.setEmail(user.getEmail());
		res.setName(user.getName());
		res.setBelong(user.getBelong());
		res.setPhoneNumber(user.getPhoneNumber());
		res.setBrn(user.getBrn());
		res.setType(user.getType());
		res.setStatusCode(statusCode);
		return res;
	}




}