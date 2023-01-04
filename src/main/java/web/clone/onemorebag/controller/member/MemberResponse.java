package web.clone.onemorebag.controller.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import web.clone.onemorebag.entity.member.Grade;
import web.clone.onemorebag.entity.member.Member;

@Getter
@AllArgsConstructor
public class MemberResponse {

    private String name;
    private String phoneNumber;
    private String email;
    private String birth;
    private String address;
    private Grade grade;

    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getName(),
                member.getPhoneNumber(),
                member.getEmail(),
                member.getBirth(),
                member.getAddress(),
                member.getGrade());
    }

    public static Page<MemberResponse> toDtoList(Page<Member> memberList) {
        return memberList.map(m -> {
            return new MemberResponse(
                    m.getName(),
                    m.getPhoneNumber(),
                    m.getEmail(),
                    m.getBirth(),
                    m.getAddress(),
                    m.getGrade()
            );
        });
    }
}
