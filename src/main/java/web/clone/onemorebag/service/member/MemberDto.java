package web.clone.onemorebag.service.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MemberDto {
    private String id;
    private String password;
    private String email;
    private String name;
}
