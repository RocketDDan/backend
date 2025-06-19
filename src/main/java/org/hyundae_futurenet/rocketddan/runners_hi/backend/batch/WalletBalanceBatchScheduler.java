package org.hyundae_futurenet.rocketddan.runners_hi.backend.batch;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.feed.MemberWalletMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WalletBalanceBatchScheduler {

	private final MemberWalletMapper memberWalletMapper;

	// 5분: 300000 | 1분: 60000 | 1초: 1000
	@Scheduled(fixedRate = 10000)
	public void flushAdViews() {

		memberWalletMapper.calculateMemberWalletBalances();
	}
}