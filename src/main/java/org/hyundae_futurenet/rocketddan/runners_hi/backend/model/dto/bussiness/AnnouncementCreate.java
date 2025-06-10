package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.bussiness;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AnnouncementCreate {

	private Long announcementId;

	private String announcementType;

	private String title;

	private String content;

	private Long createdBy;
}
