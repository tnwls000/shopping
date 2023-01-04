package web.clone.onemorebag.entity.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import web.clone.onemorebag.common.exception.form.order.AlreadyCancelOrderException;
import web.clone.onemorebag.common.exception.form.order.AlreadyCompDeliveryException;
import web.clone.onemorebag.entity.OrderItem;
import web.clone.onemorebag.entity.delivery.Delivery;
import web.clone.onemorebag.entity.delivery.DeliveryStatus;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Member;

import static org.assertj.core.api.Assertions.*;


class OrderTest {

    @Nested
    @DisplayName("주문 취소")
    class cancelOrder {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member("tnwls", "tnwls12345678", "tnwls@naver.com", "박수진");
            Delivery delivery = new Delivery("서울특별시 서대문구");

            Item item1 = new Item("가방1", 1000, 10, Category.BAG, "최고심");
            Item item2 = new Item("가방2", 2000, 20, Category.BAG, "최고심");

            OrderItem orderItem1 = OrderItem.createOrderItem(item1, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(item2, 2);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            //when
            order.cancel();

            //then
            assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
            assertThat(item1.getStockQuantity()).isEqualTo(10);
            assertThat(item2.getStockQuantity()).isEqualTo(20);
        }

        @DisplayName("실패 - 이미 배송완료된 주문")
        @Test
        void failByDeliveryStatus() {
            //given
            Member member = new Member("tnwls", "tnwls12345678", "tnwls@naver.com", "박수진");
            Delivery delivery = new Delivery("서울특별시 서대문구");

            Item item1 = new Item("가방1", 1000, 10, Category.BAG, "최고심");
            Item item2 = new Item("가방2", 2000, 20, Category.BAG, "최고심");

            OrderItem orderItem1 = OrderItem.createOrderItem(item1, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(item2, 2);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            //when
            delivery.setStatus(DeliveryStatus.COMP);
            order.setDelivery(delivery);

            //then
            assertThatThrownBy(order::cancel)
                    .isExactlyInstanceOf(AlreadyCompDeliveryException.class);
        }

        @DisplayName("실패 - 이미 취소된 주문")
        @Test
        void failByOrderStatus() {
            //given
            Member member = new Member("tnwls", "tnwls12345678", "tnwls@naver.com", "박수진");
            Delivery delivery = new Delivery("서울특별시 서대문구");

            Item item1 = new Item("가방1", 1000, 10, Category.BAG, "최고심");
            Item item2 = new Item("가방2", 2000, 20, Category.BAG, "최고심");

            OrderItem orderItem1 = OrderItem.createOrderItem(item1, 2);
            OrderItem orderItem2 = OrderItem.createOrderItem(item2, 2);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

            //when
            order.setStatus(OrderStatus.CANCEL);

            //then
            assertThatThrownBy(order::cancel)
                    .isExactlyInstanceOf(AlreadyCancelOrderException.class);
        }
    }

    @DisplayName("총 주문 가격 조회")
    @Test
    void getTotalPrice() {
        //given
        Member member = new Member("tnwls", "tnwls12345678", "tnwls@naver.com", "박수진");
        Delivery delivery = new Delivery("서울특별시 서대문구");

        Item item1 = new Item("가방1", 1000, 10, Category.BAG, "최고심");
        Item item2 = new Item("가방2", 2000, 20, Category.BAG, "최고심");

        OrderItem orderItem1 = OrderItem.createOrderItem(item1, 2);
        OrderItem orderItem2 = OrderItem.createOrderItem(item2, 2);

        Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);

        //when
        //then
        assertThat(order.getTotalPrice()).isEqualTo(6000);
    }
}