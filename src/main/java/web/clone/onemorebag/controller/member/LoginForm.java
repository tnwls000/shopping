package web.clone.onemorebag.controller.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class LoginForm {

    @NotBlank(message = "아이디를 입력해주세요")
    private String memberId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(min = 10, message = "비밀번호를 10자 이상 입력해주세요")
    private String password;
}
