package web.clone.onemorebag.controller.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.clone.onemorebag.common.auth.LoginMember;
import web.clone.onemorebag.common.auth.SessionConst;
import web.clone.onemorebag.common.exception.form.CustomFormException;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.service.member.MemberDto;
import web.clone.onemorebag.service.member.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import static web.clone.onemorebag.common.exception.api.ApiExceptionType.NOT_FOUND_ACCOUNT;

@Controller
@RequestMapping("/member")
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    @GetMapping("/signup")
    public String signUpPage(MemberSaveForm form) {
        return "signup";
    }

    @Transactional
    @PostMapping("/signup")
    public String signUp(@Valid MemberSaveForm form, BindingResult bindingResult) {
        try {
            if (!bindingResult.hasFieldErrors()) {
                memberService.signUp(new MemberDto(
                        form.getMemberId(),
                        form.getPassword(),
                        form.getEmail(),
                        form.getName()));
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getMessage(), e.getField(), e.getErrorCode());
        }

        if (bindingResult.hasErrors()) {
            return "signup";
        }
        return "redirect:/member/login";
    }

    /**
     * 로그인
     */
    @GetMapping("/login")
    public String loginPage(LoginForm form) {
        return "login";
    }

    @Transactional
    @PostMapping("/login")
    public String login(@Valid LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        Member findMember = null;
        try {
            if (!bindingResult.hasFieldErrors()) {
                findMember = memberService.signIn(form.getMemberId(), form.getPassword());
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getMessage(), e.getField(), e.getErrorCode());
        }

        if (bindingResult.hasErrors()) {
            return "login";
        }
        validateNullMember(findMember);
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, LoginMember.from(findMember));

        return "redirect:/";
    }

    private static void validateNullMember(Member findMember) {
        if (findMember == null) {
            throw NOT_FOUND_ACCOUNT.getException();
        }
    }

    /**
     * 로그아웃
     */
    @Transactional
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //세션 제거
        }
        return "redirect:/";
    }
}
