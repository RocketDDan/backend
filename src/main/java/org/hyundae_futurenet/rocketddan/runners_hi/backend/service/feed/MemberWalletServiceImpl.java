package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed.MemberWalletMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberWalletServiceImpl implements MemberWalletService {

	private final MemberWalletMapper memberWalletMapper;

	@Override
	public void setCharge(long feedId, long memberId, long chargeAmount) {

		memberWalletMapper.save(feedId, memberId, chargeAmount);
	}
}
