package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import java.util.List;
import java.util.UUID;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.converter.CommentDetailResponseConverter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.converter.FeedListResponseConverter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.CommentDetailSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.FeedListSource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.business.KakaoPaySource;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.FeedSearchFilter;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.KakaoPayApproveRequest;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.CommentDetailResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response.feed.FeedListResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.external_dto.response.KakaoPayReadyResponse;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed.FeedCommentService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed.FeedFileService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed.FeedLikeService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed.FeedService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.feed.MemberWalletService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.log.FeedViewLogService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.service.pay.KakaoPayService;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file.S3FileUtil;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.util.payment.KakaoPayUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedFacadeImpl implements FeedFacade {

	private final FeedService feedService;

	private final FeedFileService feedFileService;

	private final FeedCommentService feedCommentService;

	private final FeedLikeService feedLikeService;

	private final KakaoPayService kakaoPayService;

	private final MemberWalletService memberWalletService;

	private final FeedViewLogService feedViewLogService;

	private final FeedListResponseConverter feedListResponseConverter;

	private final CommentDetailResponseConverter commentDetailResponseConverter;

	private final S3FileUtil s3FileUtil;

	private final KakaoPayUtil kakaoPayUtil;

	@Override
	public List<FeedListResponse> searchFeedsByFilter(long loginMemberId, FeedSearchFilter feedSearchFilter) {
		// 1. 정보 가져오기
		List<FeedListSource> feedListSources = feedService.searchFeedsByFilter(loginMemberId, feedSearchFilter);
		// 2. filePath -> url 로 변환
		return feedListSources.stream()
			.map(feedListResponseConverter::toDto)
			.toList();
	}

	@Override
	@Transactional
	// TODO: feed_file 테이블에 피드 파일 정보 저장에서 에러 시 S3에는 파일이 올라감
	public void uploadFeed(
		long loginMemberId,
		String content,
		Double lat, Double lng,
		List<MultipartFile> fileList) {
		// feed 테이블에 피드 정보 저장
		long feedId = feedService.save(loginMemberId, content, lat, lng);
		// s3 파일 저장소에 피드 파일 저장
		List<String> uploadedfilePathList = s3FileUtil.uploadFeedFile(fileList, feedId);
		// feed_file 테이블에 피드 파일 정보 저장
		feedFileService.save(loginMemberId, feedId, uploadedfilePathList);
	}

	@Override
	@Transactional
	public KakaoPayReadyResponse uploadFeedByCompany(
		long loginMemberId,
		String content,
		Double lat, Double lng,
		List<MultipartFile> fileList,
		int payAmount
	) {

		// feed 테이블에 피드 정보 저장 (STATUS: WAIT)
		long feedId = feedService.saveAdvertiseFeedWithStatusWait(loginMemberId, content, lat, lng);
		// s3 파일 저장소에 피드 파일 저장
		List<String> uploadedfilePathList = s3FileUtil.uploadFeedFile(fileList, feedId);
		// feed_file 테이블에 피드 파일 정보 저장
		feedFileService.save(loginMemberId, feedId, uploadedfilePathList);
		// 결제 요청 및 결제 요청 정보 저장
		String partnerOrderId = "feed_" + feedId + "_" + UUID.randomUUID();
		KakaoPayReadyResponse response = kakaoPayUtil.kakaoPayReady(
			partnerOrderId,  // 주문번호 (생성 필요)
			String.valueOf(loginMemberId),
			"홍보피드결제",
			payAmount
		);

		// 클라이언트에게 주기 위해 response에 추가해서 넣어주기
		response.setPartner_order_id(partnerOrderId);
		kakaoPayService.save(feedId, response.getTid(), partnerOrderId, loginMemberId, payAmount);
		return response;
	}

	@Override
	@Transactional
	public void deleteFeed(long loginMemberId, long feedId) {
		// 피드 데이터 있는지 확인 후 없으면 예외 던짐 (관리자도 삭제 가능)
		feedService.assertFeedExists(loginMemberId, feedId);
		// 피드 파일 데이터 가져오기
		List<String> filePathList = feedFileService.searchFilePathList(feedId);
		// 피드 파일 데이터 삭제하기
		feedFileService.deleteAll(loginMemberId, feedId);
		// 피드 댓글 데이터 삭제하기
		feedCommentService.deleteAll(feedId);
		// 피드 데이터 삭제
		feedService.delete(feedId);
		// S3에 저징된 피드와 관련된 파일들 삭제하기
		s3FileUtil.removeFiles(filePathList);
	}

	@Override
	public void likeFeed(long loginMemberId, long feedId) {

		feedLikeService.like(loginMemberId, feedId);
	}

	@Override
	public void unlikeFeed(long loginMemberId, long feedId) {

		feedLikeService.unlike(loginMemberId, feedId);
	}

	@Override
	public void registerComment(long loginMemberId, long feedId, String comment) {

		feedCommentService.register(loginMemberId, feedId, comment);
	}

	@Override
	public void updateComment(long loginMemberId, long feedId, long commentId, String newComment) {
		// 해당하는 댓글 없으면 예외 던지기
		feedCommentService.assertCommentExists(loginMemberId, feedId, commentId);
		// 댓글 수정
		feedCommentService.update(commentId, newComment);
	}

	@Override
	public void deleteCommit(long loginMemberId, long feedId, long commentId) {
		// 해당하는 댓글 없으면 예외 던지기
		feedCommentService.assertCommentExists(loginMemberId, feedId, commentId);
		// 댓글 삭제
		feedCommentService.delete(commentId);
	}

	@Override
	public List<CommentDetailResponse> searchCommentList(long loginMemberId, long feedId) {
		// 댓글 목록 가져오기
		List<CommentDetailSource> commentDetailSources = feedCommentService.searchCommentList(loginMemberId, feedId);
		// 프로필 파일 path를 url로 변환
		return commentDetailSources.stream()
			.map(commentDetailResponseConverter::toDto)
			.toList();
	}

	@Override
	@Transactional
	public void approveFeedPay(KakaoPayApproveRequest kakaoPayApproveRequest) {

		// DB 에서 결제 요청 정보 가져오기
		KakaoPaySource kakaoPaySource = kakaoPayService.getPaySource(kakaoPayApproveRequest.getPartnerOrderId());

		// 카카오 서버로 요청 보내 카카오 페이 승인
		kakaoPayUtil.kakaoPayApprove(
			kakaoPaySource.getTid(),
			kakaoPayApproveRequest.getPartnerOrderId(),
			kakaoPaySource.getPartnerUserId(),
			kakaoPayApproveRequest.getPgToken());

		// feed의 승인 상태 APPROVED로 수정하기
		feedService.updateAdvertiseFeedStatusWithApproved(kakaoPaySource.getFeedId());

		// KAKAO PAY Status 수정
		kakaoPayService.updateStatus(kakaoPaySource.getTid(), "SUCCESS");

		// 충전/잔여 금액 세팅
		memberWalletService.setCharge(
			kakaoPaySource.getPartnerUserId(),
			kakaoPaySource.getFeedId(),
			kakaoPaySource.getChargeAmount());
	}

	@Override
	public void addViewLog(long memberId, long feedId) {

		feedViewLogService.addViewLog(memberId, feedId);
	}

	@Override
	public void addViewLogByIp(String ip, long feedId) {

		feedViewLogService.addViewLogByIp(ip, feedId);
	}

	@Override
	@Transactional
	public void updateFeed(long loginMemberId, long feedId, String newContent, Double newLat, Double newLng,
		List<MultipartFile> newfileList) {
		// 피드 데이터 있는지 확인 후 없으면 예외 던짐 (관리자도 삭제 가능)
		feedService.assertFeedExists(loginMemberId, feedId);
		// 피드 파일 데이터 가져오기
		List<String> filePathList = feedFileService.searchFilePathList(feedId);
		// 피드 파일 데이터 삭제하기
		feedFileService.deleteAll(loginMemberId, feedId);
		// S3에 저징된 피드와 관련된 파일들 삭제하기
		s3FileUtil.removeFiles(filePathList);
		// s3 파일 저장소에 새로운 피드 파일 저장
		List<String> uploadedfilePathList = s3FileUtil.uploadFeedFile(newfileList, feedId);
		// feed_file 테이블에 피드 파일 정보 저장
		feedFileService.save(loginMemberId, feedId, uploadedfilePathList);
		// 피드 수정
		feedService.update(feedId, newContent, newLat, newLng);
	}
}
