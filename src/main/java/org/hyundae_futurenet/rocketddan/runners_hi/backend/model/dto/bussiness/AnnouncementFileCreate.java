package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementFileCreate {

	private Long announcementFileId;

	private Long announcementId;

	private String filePath;

	private Long createdBy;
}
