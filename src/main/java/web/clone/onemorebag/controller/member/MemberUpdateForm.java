package web.clone.onemorebag.controller.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberUpdateForm {

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    @Size(max = 20, message = "이름을 20자 내로 입력해주세요")
    private String name;

    @NotBlank(message = "전화번호를 입력해주세요")
    @Pattern(regexp = "/^\\d{3}-\\d{3,4}-\\d{4}$/",
            message = "전화번호를 010-1234-56678의 형식으로 입력해주세요")
    private String phoneNumber;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "유효한 이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "생년월일을 입력해주세요")
    @Pattern(regexp = "/^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/",
            message = "생년월일을 yyyy-mm-dd의 형식으로 입력해주세요")
    private String birth;

    @NotBlank(message = "주소를 입력해주세요")
    @Size(max = 100, message = "주소를 100자 내로 입력해주세요")
    private String address;
}
