package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.auth;

import lombok.Getter;

@Getter
public class Accessor {

	private final Long memberId;

	private final Authority authority;

	private Accessor(final Long memberId, final Authority authority) {

		this.memberId = memberId;
		this.authority = authority;
	}

	public static Accessor guest() {

		return new Accessor(0L, Authority.GUEST);
	}

	public static Accessor user(final Long memberId) {

		return new Accessor(memberId, Authority.USER);
	}

	public static Accessor admin(final Long memberId) {

		return new Accessor(memberId, Authority.ADMIN);
	}

	public static Accessor company(final Long memberId) {

		return new Accessor(memberId, Authority.COMPANY);
	}

	public boolean isGuest() {

		return Authority.GUEST.equals(authority);
	}

	public boolean isMember() {

		return Authority.USER.equals(authority);
	}

	public boolean isAdmin() {

		return Authority.ADMIN.equals(authority);
	}

	public boolean isCompany() {

		return Authority.COMPANY.equals(authority);
	}
}
