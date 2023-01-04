package web.clone.onemorebag.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.clone.onemorebag.common.auth.Login;
import web.clone.onemorebag.common.auth.LoginMember;
import web.clone.onemorebag.common.exception.form.CustomFormException;
import web.clone.onemorebag.controller.member.MemberUpdateForm;
import web.clone.onemorebag.service.member.MemberService;

import javax.validation.Valid;
import java.io.IOException;

import static web.clone.onemorebag.common.exception.form.FormExceptionType.INCORRECT_PASSWORD;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MemberService memberService;

    /**
     * 회원정보수정
     */
    @PostMapping("/modify")
    public String updateMember(@Login LoginMember loginMember,
                               @Valid MemberUpdateForm form,
                               BindingResult bindingResult, Model model) throws IOException {
        validatePassword(loginMember, form.getPassword());
        try {
            if (!bindingResult.hasFieldErrors()) {
                memberService.updateMember(loginMember.getMemberId(), form.getPhoneNumber(), form.getEmail(), form.getBirth(), form.getAddress());
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getMessage(), e.getField(), e.getErrorCode());
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            return "mypage/modify";
        }
        return "redirect:/mypage/modify";
    }

    private void validatePassword(LoginMember loginMember, String password) {
        if (!loginMember.getPassword().equals(password)) {
            throw INCORRECT_PASSWORD.getException();
        }
    }
}
