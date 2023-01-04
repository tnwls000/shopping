package web.clone.onemorebag.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import web.clone.onemorebag.entity.ItemLike;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.repository.ItemLikeRepository;
import web.clone.onemorebag.repository.item.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemLikeServiceTest {

    @Mock ItemRepository itemRepository;
    @Mock ItemLikeRepository itemLikeRepository;
    @InjectMocks ItemLikeService itemLikeService;

    @Nested
    @DisplayName("좋아요 되어 있는 객체 조회")
    class findItemLikes {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            IntStream.range(0,10).forEach(i -> {
                Item item = new Item("볼펜" + i, 1000, 100, Category.STATIONERY, "최고심");

                when(itemRepository.getItemsByOrderByIdDesc(
                        PageRequest.of(0, 10)))
                        .thenReturn(new ArrayList<>());

                ItemLike itemLike = new ItemLike(member, item);
                lenient().when(itemLikeRepository.findByMemberIdAndItemId(
                        member.getId(), item.getId()))
                        .thenReturn(Optional.of(itemLike));
            });
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> itemLikeService.findItemLikes("tnwls", PageRequest.of(0, 10)));
        }

        @DisplayName("결과 없음")
        @Test
        void isEmpty() {
            //given
            Member member = new Member("wlswls", "tnwls123456", "tnwlssla20@naver.com", "박수진");

            //when
            //then
            List<ItemLike> itemLikes = itemLikeService.findItemLikes("wlswls", PageRequest.of(0, 1));
            assertThat(itemLikes.size()).isEqualTo(0);
        }
    }


}