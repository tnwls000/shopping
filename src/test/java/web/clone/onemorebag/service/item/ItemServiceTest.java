package web.clone.onemorebag.service.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import web.clone.onemorebag.common.exception.api.httpstatusexception.NotFoundException;
import web.clone.onemorebag.common.exception.form.item.DuplicateItemByIdException;
import web.clone.onemorebag.common.exception.form.item.DuplicateItemByNameException;
import web.clone.onemorebag.entity.ItemLike;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.repository.ItemLikeRepository;
import web.clone.onemorebag.repository.MemberRepository;
import web.clone.onemorebag.repository.item.ItemRepository;
import web.clone.onemorebag.repository.item.ItemSearchDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    ItemRepository itemRepository;
    @Mock
    MemberRepository memberRepository;
    @Mock
    ItemLikeRepository itemLikeRepository;
    @InjectMocks
    ItemService itemService;

    @BeforeEach
    void init() {
        IntStream.range(0, 21).forEach(i -> {
            Item item = new Item("인형" + i, 1000, 100, Category.DOLL, "최고심");
            lenient().when(itemRepository.findById(item.getId()))
                    .thenReturn(Optional.of(item));
        });
    }

    @Nested
    @DisplayName("상품 등록")
    class addItem {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            ItemDto itemDto = new ItemDto("볼펜", 1000, 100, Category.STATIONERY, "최고심", new ArrayList<>());

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> itemService.addItem(itemDto));
        }

        @DisplayName("실패 - 이름 중복")
        @Test
        void failByDuplicateName() {
            //given
            String name = "볼펜";
            ItemDto itemDto = new ItemDto(name, 1000, 100, Category.STATIONERY, "최고심", new ArrayList<>());
            Item item = new Item(itemDto.getName(), itemDto.getPrice(), itemDto.getStockQuantity(), itemDto.getCategory(), itemDto.getBrand());
            when(itemRepository.findByName(name)).thenReturn(Optional.of(item));
            //when
            //then
            assertThatThrownBy(() -> itemService.addItem(itemDto))
                    .isExactlyInstanceOf(DuplicateItemByNameException.class);
        }

        @DisplayName("실패 - 아이디 중복")
        @Test
        void failByDuplicateId() {
            //given
            ItemDto itemDto = new ItemDto("볼펜", 1000, 100, Category.STATIONERY, "최고심", new ArrayList<>());
            Item item = new Item(itemDto.getName(), itemDto.getPrice(), itemDto.getStockQuantity(), itemDto.getCategory(), itemDto.getBrand());
            when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
            //when
            //then
            assertThatThrownBy(() -> itemService.addItem(itemDto))
                    .isExactlyInstanceOf(DuplicateItemByIdException.class);
        }
    }

    @Nested
    @DisplayName("상품 조회 - 1개")
    class getItem {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Item item = new Item("최고심 인형", 1000, 100, Category.DOLL, "최고심");
            when(itemRepository.findById(item.getId()))
                    .thenReturn(Optional.of(item));
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> itemService.getItem(item.getId()));
        }

        @DisplayName("실패 - 상품 없음")
        @Test
        void failById() {
            //given
            //when
            //then
            assertThatThrownBy(() -> itemService.getItem(1L))
                    .isExactlyInstanceOf(NotFoundException.class);
        }
    }

    @Test
    @DisplayName("상품 조회 - 여러개")
    void getItems() {
        //given
        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> itemService.getItems(PageRequest.of(0, 20)));
    }

    @Test
    @DisplayName("카테고리별 상품 조회")
    void getItemsByCategory() {
        //given
        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> itemService.getItemsByCategory(Category.DOLL, PageRequest.of(0, 20)));
    }


    @Nested
    @DisplayName("상품 수정")
    class updateItem {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Item item = new Item("최고심 인형", 1000, 100, Category.DOLL, "최고심");
            when(itemRepository.findById(item.getId()))
                    .thenReturn(Optional.of(item));
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> itemService.updateItem(item.getId(), "최고심 인형2", 2000, 200, new ArrayList<>()));
        }

        @DisplayName("실패 - 상품 없음")
        @Test
        void failById() {
            //given
            //when
            //then
            assertThatThrownBy(() -> itemService.updateItem(1L, "최고심 인형2", 2000, 200, new ArrayList<>()))
                    .isExactlyInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("상품 삭제")
    class deleteItem {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Item item = new Item("최고심 인형", 1000, 100, Category.DOLL, "최고심");
            when(itemRepository.findById(item.getId()))
                    .thenReturn(Optional.of(item));
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> itemService.deleteItem(item.getId()));
        }

        @DisplayName("실패 - 상품 없음")
        @Test
        void failById() {
            //given
            //when
            //then
            assertThatThrownBy(() -> itemService.deleteItem(1L))
                    .isExactlyInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("상품 검색")
    class searchItems {
        @DisplayName("성공")
        @Test
        void success() {
            //given
            Item item = new Item("최고심 인형", 1000, 100, Category.DOLL, "최고심");
            List<ItemSearchDto> itemSearchDtos = new ArrayList<>();
            itemSearchDtos.add(new ItemSearchDto(
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getStockQuantity(),
                    item.getCategory(),
                    item.getBrand()));
            when(itemRepository.searchItems(anyString(), any(Pageable.class)))
                    .thenReturn(itemSearchDtos);
            when(itemRepository.searchItemFiles(item.getId()))
                    .thenReturn(new ArrayList<>());
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> itemService.searchItems("최고심", PageRequest.of(0, 1)));
        }

        @DisplayName("실패 - 결과 없음")
        @Test
        void isEmpty() {
            //given
            when(itemRepository.searchItems(anyString(), any(Pageable.class)))
                    .thenReturn(new ArrayList<>());
            //when
            //then
            assertThatThrownBy(() -> itemService.searchItems("인형", PageRequest.of(0, 1)))
                    .isExactlyInstanceOf(NotFoundException.class);
        }
    }

    @DisplayName("상품 좋아요 목록 조회")
    @Test
    void findLikes() {
        //given
        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> itemService.findLikes("tnwls", PageRequest.of(0, 1)));
    }

    @DisplayName("상품 좋아요 조회")
    @Test
    void findLike() {
        //given
        //when
        //then
        assertThatNoException()
                .isThrownBy(() -> itemService.findLike("tnwls", 1L));
        int like = itemService.findLike("tnwls", 1L);
        assertThat(like).isEqualTo(0);
    }

    @Nested
    @DisplayName("상품 좋아요 기능")
    class saveAndCancelLike {

        @DisplayName("좋아요")
        @Test
        void saveLike() {
            //given
            Member member = new Member("tnwls", "tnwls24325235", "tnwls@naver.com", "박수진");
            when(memberRepository.findById("tnwls"))
                    .thenReturn(Optional.of(member));

            Item item = new Item("최고심 인형", 1000, 100, Category.DOLL, "최고심");
            when(itemRepository.findById(item.getId()))
                    .thenReturn(Optional.of(item));

            when(itemLikeRepository.findByMemberIdAndItemId("tnwls", item.getId()))
                    .thenReturn(Optional.empty());

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> itemService.saveLike(member.getId(), item.getId()));
            int like = itemService.saveLike(member.getId(), item.getId());
            assertThat(like).isEqualTo(1);
        }

        @DisplayName("좋아요 취소")
        @Test
        void cancelLike() {
            //given
            Member member = new Member("tnwls", "tnwls24325235", "tnwls@naver.com", "박수진");
            Item item = new Item("최고심 인형", 1000, 100, Category.DOLL, "최고심");
            ItemLike itemLike = new ItemLike(member, item);
            when(itemLikeRepository.findByMemberIdAndItemId("tnwls", item.getId()))
                    .thenReturn(Optional.of(itemLike));

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> itemService.saveLike(member.getId(), item.getId()));
            int like = itemService.saveLike(member.getId(), item.getId());
            assertThat(like).isEqualTo(0);
        }
    }
}