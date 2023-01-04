package web.clone.onemorebag.entity.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import web.clone.onemorebag.entity.BaseEntity;
import web.clone.onemorebag.entity.order.Order;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static web.clone.onemorebag.common.exception.form.FormExceptionType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements Persistable {

    @Id
    @Column(name = "member_id")
    private String id;

    private String password;
    private String phoneNumber;
    private String email;
    private String name;
    private String birth;
    private String address;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @OneToMany(mappedBy = "member", orphanRemoval = true) //양방향
    private List<Order> orders = new ArrayList<>();

    public Member(String id, String password, String email, String name) {
        validateId(id);
        this.id = id;
        validatePassword(password);
        this.password = password;
        validateEmail(email);
        this.email = email;
        this.name = name;
        setGrade(Grade.GENERAL);
    }

    public void changeMemberInfo(String phoneNumber, String email, String birth, String address) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birth = birth;
        this.address = address;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    //==검증==
    public void validateId(String id) {
        if (!isMemberId(id)) {
            throw INVALID_MEMBERID.getException();
        }
    }

    private boolean isMemberId(String id) {
        // 영문소문자/숫자, 4-16자
//        return id.matches("^[a-z][a-z0-9]{3,15}$");
        return id.matches("^(?!(?:[0-9]+)$)([a-z]|[0-9a-z]){4,16}$");
    }

    public void validatePassword(String password) {
        if (!isPassword(password)) {
            throw INVALID_PASSWORD.getException();
        }
    }

    private boolean isPassword(String password) {
        // 영문대소문자/숫자/특수문자 중 2가지 이상 조합, 10-16자
        // 특수문자 허용: ~`!@#$%^()_{}|;:<>,.?/
        return password.matches("^(?!((?:[A-Za-z]+)|(?:[~`!@#$%^()_{}|;:<>,.?/]+)|(?:[0-9]+))$)[A-Za-z\\d~`!@#$%^()_{}|;:<>,.?/]{10,16}$");
    }

    private void validateEmail(String email) {
        if (!isEmail(email)) {
            throw INVALID_EMAIL.getException();
        }
    }

    public boolean isEmail(String email) {
        return email.matches("^[A-Za-z\\d_.-]+@(.+)$");
    }

    /**
     * id가 자동 생성되는 식별자가 아니므로
     * 영속화 시 merge가 아닌 persist 해주기 위해
     * Persistable로 isNew() 직접 구현
     */
    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }
}
