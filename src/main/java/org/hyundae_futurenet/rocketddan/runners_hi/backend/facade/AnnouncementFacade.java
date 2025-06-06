package org.hyundae_futurenet.rocketddan.runners_hi.backend.facade;

import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.dto.request.AnnouncementCreateRequest;

public interface AnnouncementFacade {

	void createAnnouncement(AnnouncementCreateRequest request, Long memberId, String role);
}
