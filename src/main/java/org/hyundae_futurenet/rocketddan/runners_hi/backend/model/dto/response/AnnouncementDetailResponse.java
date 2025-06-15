package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDetailResponse {

	private Long announcementId;

	private Long createdBy;

	private String title;

	private String content;

	private String createdAt;

	private String updatedAt;

	private String nickname;

	private List<String> attachPaths;
}
