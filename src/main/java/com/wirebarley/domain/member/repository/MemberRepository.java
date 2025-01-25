package com.wirebarley.domain.member.repository;

import com.wirebarley.domain.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Member findById(String username);

    @Query("select m from Member AS m where m.memberNo = :memberNo")
    Optional<Member> selectMemberEntityByMemberNo(@Param("memberNo") long memberNo);

    @Query("select m from Member AS m where m.id = :memberId")
    Optional<Member> selectMemberEntityByMemberId(@Param("memberId") String memberId);
}