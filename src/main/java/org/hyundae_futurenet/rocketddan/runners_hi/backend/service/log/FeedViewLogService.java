package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.log;

public interface FeedViewLogService {

	void addViewLog(long memberId, long feedId);

	void addViewLogByIp(String ip, long feedId);
}
