package com.b101.recruit.domain.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class UserWallet extends BaseEntity {

	@OneToOne
	@JoinColumn(name = "user_id")
	private User user; // 회원 ID(FK)
	
	private String address;
	private String balance;
	private String receiving_count;
//	private String cash;
	
}
