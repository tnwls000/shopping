package web.clone.onemorebag.controller.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberSaveForm {

    @NotBlank(message = "아이디를 입력해주세요")
    private String memberId;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Size(max = 16, message = "비밀번호를 16자 내로 입력해주세요")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "유효한 이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 20, message = "이름을 20자 내로 입력해주세요")
    private String name;
}
