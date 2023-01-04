package web.clone.onemorebag.repository.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import web.clone.onemorebag.common.WebConfig;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.item.ItemFile;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(WebConfig.class)
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void init() {
        IntStream.range(1, 51).forEach(i -> {
            String name;
            Category category;
            if (i < 31) {
                name = "가방";
                category = Category.BAG;
            } else {
                name = "볼펜";
                category = Category.STATIONERY;
            }
            Item item = new Item(name + i, 1000, 100, category, "최고심");
            item.changeItemFile(new ItemFile("upload", "store", "png"));
            item.changeItemFile(new ItemFile("upload2", "store2", "jpg"));
            itemRepository.save(item);
        });
    }

    @DisplayName("상품 조회 - 여러개")
    @Test
    void getItemsByOrderIdDesc() {
        //given
        int pageSize = 50;
        //when
        //then
        List<Item> items = itemRepository.getItemsByOrderByIdDesc(PageRequest.of(0, pageSize));
        assertThat(items).hasSize(pageSize);
    }

    @Nested
    @DisplayName("이름으로 상품 조회")
    class findByName {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            String name = "가방1";
            //when
            //then
            Item item = itemRepository.findByName(name).orElse(null);
            assertThat(item).isNotNull();
        }

        @DisplayName("실패 - 동일한 이름의 상품 없음")
        @Test
        void failByName() {
            //given
            //when
            //then
            Item item = itemRepository.findByName(" ").orElse(null);
            assertThat(item).isNull();
        }
    }

    @Nested
    @DisplayName("카테고리별 상품 조회")
    class findByCategory {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            int bagPageSize = 30;
            int stationeryPageSize = 20;
            //when
            //then
            List<Item> bagItems = itemRepository.findByCategory(
                    Category.BAG,
                    PageRequest.of(0, bagPageSize
                    ));
            List<Item> stationeryItems = itemRepository.findByCategory(
                    Category.STATIONERY,
                    PageRequest.of(0, stationeryPageSize
                    ));

            assertThat(bagItems).hasSize(bagPageSize);
            assertThat(stationeryItems).hasSize(stationeryPageSize);
        }

        @DisplayName("실패 - 해당 카테고리 상품 없음")
        @Test
        void failByCategory() {
            //given
            //when
            //then
            List<Item> items = itemRepository.findByCategory(Category.ACC, PageRequest.of(0, 1));
            assertThat(items.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("상품 검색")
    class searchItems {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            String search = "방";
            int pageSize = 30;
            //when
            //then
            List<ItemSearchDto> itemDtos = itemRepository.searchItems(
                    search, PageRequest.of(0, pageSize));

            assertThatNoException().isThrownBy(() -> itemDtos.stream().map(i ->
                            itemRepository.searchItemFiles(i.getId())));
            assertThat(itemDtos).hasSize(pageSize);
        }

        @DisplayName("실패 - 결과 없음")
        @Test
        void fail() {
            //given
            int pageSize = 30;
            //when
            //then
            List<ItemSearchDto> itemDtos = itemRepository.searchItems(
                    " ", PageRequest.of(0, pageSize));
            assertThat(itemDtos.size()).isEqualTo(0);
        }
    }
}