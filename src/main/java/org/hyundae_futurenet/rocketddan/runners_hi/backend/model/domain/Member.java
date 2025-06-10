package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain;

import java.time.LocalDateTime;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.constant.Role;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {

	private Long memberId;

	private String email;

	private String nickname;

	private String phone;

	private String pwdHash;

	private Role role;

	private String profilePath;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private Long updatedBy;

	private LocalDateTime deletedAt;

	private Long deletedBy;

	public Member(String email, String nickname, String phone, String pwdHash, Role role) {

		this.email = email;
		this.nickname = nickname;
		this.phone = phone;
		this.pwdHash = pwdHash;
		this.role = role;
		this.createdAt = LocalDateTime.now();
		this.updatedAt = LocalDateTime.now();
	}
}
