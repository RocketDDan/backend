package org.hyundae_futurenet.rocketddan.runners_hi.backend.service.log;

/**
 * 피드 조회 로그를 Redis 등에 기록하는 서비스 인터페이스입니다.
 * 로그인 사용자 또는 비로그인 사용자의 피드 조회를 구분하여 처리합니다.
 */
public interface FeedViewLogService {

	/**
	 * 로그인한 사용자의 피드 조회 로그를 기록합니다.
	 * Redis의 Set 자료구조를 활용하여 중복 조회를 방지하거나 TTL 기반 집계를 할 수 있습니다.
	 *
	 * @param memberId 조회한 회원 ID
	 * @param feedId 조회된 피드 ID
	 */
	void addViewLog(long memberId, long feedId);

	/**
	 * 비로그인 사용자의 IP 기반 피드 조회 로그를 기록합니다.
	 *
	 * @param ip 사용자 IP 주소
	 * @param feedId 조회된 피드 ID
	 */
	void addViewLogByIp(String ip, long feedId);
}