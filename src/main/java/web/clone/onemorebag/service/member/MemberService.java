package web.clone.onemorebag.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.clone.onemorebag.controller.member.MemberResponse;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.repository.MemberRepository;


import java.util.List;

import static web.clone.onemorebag.common.exception.form.FormExceptionType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional
    public String signUp(MemberDto memberDto) {
        validateDuplication(memberDto.getId());
        Member member = new Member(memberDto.getId(), memberDto.getPassword(), memberDto.getEmail(), memberDto.getName());
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplication(String id) {
        memberRepository.findById(id).ifPresent(member -> {
            throw DUPLICATE_MEMBERID.getException();
        });
    }

    /**
     * 로그인
     */
    public Member signIn(String id, String password) {
        Member findMember = findMember(id);
        validatePassword(password, findMember);
        return findMember;
    }

    private void validatePassword(String password, Member findMember) {
        if (!findMember.getPassword().equals(password)) {
            throw INCORRECT_PASSWORD.getException();
        }
    }

    /**
     * 특정 회원 조회
     */
    public Member findMember(String id) {
        return memberRepository.findById(id)
                .orElseThrow(NOT_FOUND_ACCOUNT_BY_ID::getException);
    }

    /**
     * 회원 목록 조회
     */
    public Page<MemberResponse> findMembers(Pageable pageable) {
        return MemberResponse.toDtoList(memberRepository.findAll(pageable));
    }

    /**
     * 회원 수정
     */
    @Transactional
    public void updateMember(String id, String phoneNumber, String email, String birth, String address) {
        Member findMember = findMember(id);
        findMember.changeMemberInfo(phoneNumber, email, birth, address);
    }

    /**
     * 회원 탈퇴
     */
    @Transactional
    public void deleteMember(String id) {
        memberRepository.delete(findMember(id));
    }
}
