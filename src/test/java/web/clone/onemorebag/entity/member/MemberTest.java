package web.clone.onemorebag.entity.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import web.clone.onemorebag.common.exception.form.signup.InvalidEmailException;
import web.clone.onemorebag.common.exception.form.signup.InvalidMemberIdException;
import web.clone.onemorebag.common.exception.form.signup.InvalidPasswordException;

import static org.assertj.core.api.Assertions.*;


class MemberTest {
    @Nested
    @DisplayName("생성자 검증")
    class MemberConstructorTest {

        @Test
        @DisplayName("성공")

        void success() {
            assertThatNoException()
                    .isThrownBy(() -> new Member("tnwlssla20", "230067tnwls", "tnwlssla20@naver.com", "박수진"));

        }


        @DisplayName("실패 - 유효하지 않은 아이디 형식")
        @ParameterizedTest
        @ValueSource(strings = {"tn1", "tnw", "12345", "tnwlstnwls123456789", "s?!123", "     "})
        void failById(String id) {
            assertThatThrownBy(() -> new Member(id, "230067tnwls", "tnwlssla20@naver.com", "박수진"))
                    .isExactlyInstanceOf(InvalidMemberIdException.class);
        }

        @DisplayName("실패 - 유효하지 않은 비밀번호 형식")
        @ParameterizedTest
        @ValueSource(strings = {"12tn", "[]tnwls2305", "?!~~`!@#$%^()_{}|",
                "12345567902", "tnwlstnwlsss", "12t~!n", "     "})
        void failByPassword(String password) {
            assertThatThrownBy(() -> new Member("tnwls", password, "tnwlssla20@naver.com", "박수진"))
                    .isExactlyInstanceOf(InvalidPasswordException.class);
        }

        @DisplayName("실패 - 유효하지 않은 이메일 형식")
        @ParameterizedTest
        @ValueSource(strings = {"tnwls", "@naver.com", "tnwlsslanaver", "tnwls@"})
        void failByEmail(String email) {
            assertThatThrownBy(() -> new Member("tnwls", "230067tnwls", email, "박수진"))
                    .isExactlyInstanceOf(InvalidEmailException.class);
        }
    }
}