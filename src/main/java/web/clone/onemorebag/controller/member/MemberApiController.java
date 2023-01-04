package web.clone.onemorebag.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.clone.onemorebag.common.auth.Login;
import web.clone.onemorebag.common.auth.LoginMember;
import web.clone.onemorebag.entity.member.Grade;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.service.member.MemberService;

import java.util.List;

import static web.clone.onemorebag.common.exception.api.ApiExceptionType.FORBIDDEN_ACCOUNT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 회원 목록 조회
     */
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<Page<MemberResponse>> members(@Login LoginMember loginMember, Pageable pageable) {
        validateAccess(loginMember);
        Page<MemberResponse> members = memberService.findMembers(pageable);
        return ResponseEntity.ok()
                .body(members);
    }

    private void validateAccess(LoginMember loginMember) {
        Member findMember = memberService.findMember(loginMember.getMemberId());
        if (!findMember.getGrade().equals(Grade.ADMIN)) {
            throw FORBIDDEN_ACCOUNT.getException();
        }
    }
}
