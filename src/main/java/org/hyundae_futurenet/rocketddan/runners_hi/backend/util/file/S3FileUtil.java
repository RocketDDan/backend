package org.hyundae_futurenet.rocketddan.runners_hi.backend.util.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor
public class S3FileUtil {

	private final S3Client s3Client;

	@Value("${s3.upload_bucket.name}")
	private String BUCKET_NAME;

	@Value("${s3.upload_path.member_profile}")
	private String MEMBER_PROFILE_PATH;

	@Value("${s3.upload_path.crew_profile}")
	private String CREW_PROFILE_PATH;

	@Value("${s3.upload_path.feed}")
	private String FEED_FILE_PATH;

	@Value("${s3.upload_path.announcement}")
	private String ANNOUNCEMENT_FILE_PATH;

	/// 멤버 프로필 이미지 단일  업로드
	public String uploadMemberProfile(MultipartFile file, long memberId) {

		return upload(file, MEMBER_PROFILE_PATH + memberId + "/");
	}

	/// 크루 프로필 이미지 단일 업로드
	public String uploadCrewProfile(MultipartFile file, long crewId) {

		return upload(file, CREW_PROFILE_PATH + crewId + "/");
	}

	/// 피드 파일 목록 다중 업로드
	public List<String> uploadFeedFile(List<MultipartFile> fileList, long feedId) {

		return uploadFiles(fileList, FEED_FILE_PATH + feedId + "/");
	}

	/// 공고 파일 목록 다중 업로드
	public List<String> uploadAnnouncementFile(List<MultipartFile> fileList, long announcementId) {

		return uploadFiles(fileList, ANNOUNCEMENT_FILE_PATH + announcementId + "/");
	}

	/**
	 * S3 버킷에 업로드
	 * @param file
	 * @param path : path/to/ 형식
	 * @return 저장 파일 경로
	 * @throws Exception
	 */
	private String upload(MultipartFile file, String path) {

		try {
			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
			String fullFilePath = path + filename;

			s3Client.putObject(
				PutObjectRequest.builder()
					.bucket(BUCKET_NAME)
					.key(fullFilePath)
					.contentType(file.getContentType())
					.build(),
				RequestBody.fromInputStream(file.getInputStream(), file.getSize())
			);
			return fullFilePath;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 다중 파일 업로드
	 * @param fileList
	 * @param path
	 * @return
	 */
	private List<String> uploadFiles(List<MultipartFile> fileList, String path) {

		List<String> filenameList = new ArrayList<>();

		fileList.forEach(file -> {
			String filePath = upload(file, path);
			filenameList.add(filePath);
		});
		return filenameList;
	}

	/**
	 * 다중 파일 삭제
	 * @param filePathList: 파일 경로 목록
	 */
	public void removeFiles(List<String> filePathList) {

		filePathList.forEach(filePath ->
			s3Client.deleteObject(builder -> builder
				.bucket(BUCKET_NAME)
				.key(filePath)
				.build()
			)
		);
	}
}