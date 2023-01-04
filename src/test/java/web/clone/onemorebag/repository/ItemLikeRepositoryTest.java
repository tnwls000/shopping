package web.clone.onemorebag.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import web.clone.onemorebag.common.WebConfig;
import web.clone.onemorebag.entity.ItemLike;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Member;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(WebConfig.class)
class ItemLikeRepositoryTest {

    @Autowired
    ItemLikeRepository itemLikeRepository;
    @Autowired
    EntityManager em;

    @Nested
    @DisplayName("좋아요 찾기")
    class findLike {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            String name = "볼펜";
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            Item item = new Item(name, 1000, 100, Category.ACC, "최고심");
            em.persist(member);
            em.persist(item);
            ItemLike itemLike = new ItemLike(member, item);
            itemLikeRepository.save(itemLike);

            //when
            //then
            ItemLike findItemLike = itemLikeRepository
                    .findByMemberIdAndItemId(member.getId(), item.getId())
                    .orElse(null);
            assertThat(findItemLike.getItem().getName()).isEqualTo(name);
        }

        @DisplayName("실패 - 결과 없음")
        @Test
        void isNull() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            em.persist(member);
            //when
            //then
            ItemLike itemLike = itemLikeRepository.findByMemberIdAndItemId(member.getId(), null).orElse(null);
            assertThat(itemLike).isNull();

        }
    }

    @DisplayName("좋아요 삭제")
    @Test
    void deleteItemLike() {
        //given
        Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
        Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");
        em.persist(member);
        em.persist(item);
        //when
        //then
        assertThatNoException().isThrownBy(() ->
                itemLikeRepository.deleteByMemberIdAndItemId(member.getId(), item.getId()));

    }

}