package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementListResult {

	private List<AnnouncementListResponse> announcements;

	private int totalCount;
}