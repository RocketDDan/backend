package org.hyundae_futurenet.rocketddan.runners_hi.backend.model.mapper.member;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.hyundae_futurenet.rocketddan.runners_hi.backend.model.domain.Member;

@Mapper
public interface MemberMapper {

	Optional<Member> findByEmail(String email);

	Optional<Member> findByNickname(String nickname);

	Optional<Member> findById(long memberId);

	int insertMember(Member member);

	void updateProfileImage(long memberId, String uploadedFilePath);

	boolean existsByNickname(String nickname);
}
