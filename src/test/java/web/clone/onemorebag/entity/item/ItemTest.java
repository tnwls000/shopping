package web.clone.onemorebag.entity.item;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import web.clone.onemorebag.common.exception.form.order.NotEnoughStockQuantity;

import static org.assertj.core.api.Assertions.*;

class ItemTest {

    @DisplayName("재고 추가")
    @Test
    void addStock() {
        //given
        Item item = new Item("가방", 1000, 50, Category.BAG, "최고심");

        //when
        item.addStock(50);

        //then
        assertThat(item.getStockQuantity()).isEqualTo(100);
    }

    @Nested
    @DisplayName("재고 감소")
    class removeStock {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            Item item = new Item("가방", 1000, 50, Category.BAG, "최고심");

            //when
            item.removeStock(10);

            //then
            assertThat(item.getStockQuantity()).isEqualTo(40);
        }

        @DisplayName("실패 - 재고 부족")
        @Test
        void failByStockQuantity() {
            //given
            Item item = new Item("가방", 1000, 50, Category.BAG, "최고심");

            //when
            //then
            assertThatThrownBy(() -> item.removeStock(60))
                    .isExactlyInstanceOf(NotEnoughStockQuantity.class);
        }
    }
}