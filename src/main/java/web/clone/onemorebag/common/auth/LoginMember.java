package web.clone.onemorebag.common.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import web.clone.onemorebag.entity.member.Member;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginMember {
    private String memberId;
    private String password;
    private String email;
    private String name;

    public static LoginMember from(Member member) {
        return new LoginMember(
                member.getId(),
                member.getPassword(),
                member.getEmail(),
                member.getName());
    }
}
