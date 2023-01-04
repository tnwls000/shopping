package web.clone.onemorebag.service.member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import web.clone.onemorebag.common.exception.form.signin.IncorrectPasswordException;
import web.clone.onemorebag.common.exception.form.signin.NotFoundAccountByIdException;
import web.clone.onemorebag.common.exception.form.signup.DuplicateMemberIdException;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {


    @Mock
    MemberRepository memberRepository;
    @InjectMocks
    MemberService memberService;

    @BeforeEach
    void init() {
        IntStream.range(0, 21).forEach(i -> {
            Member member = new Member("tnwls" + i,
                    "tnwls123456" + i,
                    "tnwlssla20" + i + "@naver.com",
                    "박수진" + i);
            lenient().when(memberRepository.findById("tnwls" + i))
                    .thenReturn(Optional.of(member));
        });
    }

    @Nested
    @DisplayName("회원가입")
    class signUpTest {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            MemberDto memberDto = new MemberDto("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> memberService.signUp(memberDto));
        }

        @Test
        @DisplayName("실패 - 아이디 중복")
        void failByIdDuplication() {
            //given
            String id = "tnwls";
            Member member = new Member(id, "tnwls123456", "tnwlssla20@naver.com", "박수진");
            when(memberRepository.findById(id))
                    .thenReturn(Optional.of(member));

            MemberDto memberDto = new MemberDto(id, "tnwls?tnwls", "tnwls@naver.com", "박진수");

            //then
            //when
            assertThatThrownBy(() -> memberService.signUp(memberDto))
                    .isExactlyInstanceOf(DuplicateMemberIdException.class);
        }
    }

    @Nested
    @DisplayName("로그인")
    class signInTest {


        @DisplayName("성공")
        @ParameterizedTest
        @CsvSource({
                "tnwls, 1234tnwlss",
                "tnwls20, 1234tnwlss",
                "wlswls, 1234tnwlss"
        })
        void success(String id, String password) {
            //given
            Member member = new Member(id, password, "tnwlssla@naver.com", "박수진");
            when(memberRepository.findById(id))
                    .thenReturn(Optional.of(member));
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> memberService.signIn(id, password));
        }

        @DisplayName("실패 - 존재하지 않는 계정")
        @ParameterizedTest
        @CsvSource({
                "tnwls, 1234tnwlss",
                "tnwls20, 1234tnwlss",
                "wlswls, 1234tnwlss"
        })
        void failById(String id, String password) {
            //given
            Member member = new Member(id, password, "tnwlssla@naver.com", "박수진");
            lenient().when(memberRepository.findById(id))
                    .thenReturn(Optional.of(member));
            //when
            //then
            assertThatThrownBy(() -> memberService.signIn("tnwlss", "1234"))
                    .isExactlyInstanceOf(NotFoundAccountByIdException.class);
        }

        @Test
        @DisplayName("실패 - 일지하지 않는 비밀번호")
        void failByPassword() {
            //given
            String id = "tnwls";
            Member member = new Member(id, "1234tnwlss", "tnwls@naver.com", "박수진");
            when(memberRepository.findById(id))
                    .thenReturn(Optional.of(member));

            //when
            //then
            assertThatThrownBy(() -> memberService.signIn(id, "1234"))
                    .isExactlyInstanceOf(IncorrectPasswordException.class);
        }
    }

    @Nested
    @DisplayName("특정 회원 조회")
    class findMember {

        @Test
        @DisplayName("성공")
        void success() {
            //given
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> memberService.findMember("tnwls0"));
        }

        @Test
        @DisplayName("실패 - 존재하지 않는 아이디")
        void failById() {
            //given
            //when
            //then
            assertThatThrownBy(() -> memberService.findMember("tnwls"))
                    .isExactlyInstanceOf(NotFoundAccountByIdException.class);
        }
    }

    @Test
    @DisplayName("회원 전체 조회")
    void findMembersTest() {
        //given
        List<Member> members = new ArrayList<>();
        Page<Member> pagedResponse = new PageImpl(members);
        when(memberRepository.findAll(any(Pageable.class)))
                .thenReturn(pagedResponse);
        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> memberService.findMembers(PageRequest.of(0, 1)));
    }

    @Test
    @DisplayName("회원 정보 수정")
    void updateMemberTest() {
        //given
        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> memberService.updateMember(
                        "tnwls0",
                        "01022221111",
                        "tnwlssla20@naver.com",
                        "2000-02-21",
                        "서울특별시 서대문구"));
    }

    @Test
    @DisplayName("회원 탈퇴")
    void deleteMemberTest() {
        //given
        String id = "tnwls000";
        Member member = new Member(id, "tnwls123456778", "tnwls@naver.com", "박수진");
        when(memberRepository.findById(id)).thenReturn(Optional.of(member));

        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> memberService.deleteMember(id));
        verify(memberRepository).delete(member);
    }
}