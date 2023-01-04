package web.clone.onemorebag.service.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import web.clone.onemorebag.common.exception.api.httpstatusexception.NotFoundException;
import web.clone.onemorebag.entity.OrderItem;
import web.clone.onemorebag.entity.delivery.Delivery;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.entity.order.Order;
import web.clone.onemorebag.repository.MemberRepository;
import web.clone.onemorebag.repository.OrderRepository;
import web.clone.onemorebag.repository.item.ItemRepository;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderService orderService;

    @Nested
    @DisplayName("상품 주문")
    class order {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");

            when(memberRepository.findById("tnwls"))
                    .thenReturn(Optional.of(member));
            when(itemRepository.findById(item.getId()))
                    .thenReturn(Optional.of(item));


            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> orderService.order(
                            member.getId(),
                            item.getId(),
                            "서대문구",
                            20));
        }

        @DisplayName("실패 - 존재하지 않는 계정")
        @Test
        void failByMemberId() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");

            lenient().when(itemRepository.findById(item.getId()))
                    .thenReturn(Optional.of(item));

            //when
            //then
            assertThatThrownBy(() -> orderService.order(
                    member.getId(),
                    item.getId(),
                    "서대문구",
                    20)).isExactlyInstanceOf(NotFoundException.class);
        }

        @DisplayName("실패 - 존재하지 않는 상품")
        @Test
        void failByItemId() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");

            when(memberRepository.findById("tnwls"))
                    .thenReturn(Optional.of(member));

            //when
            //then
            assertThatThrownBy(() -> orderService.order(
                    member.getId(),
                    item.getId(),
                    "서대문구",
                    20)).isExactlyInstanceOf(NotFoundException.class);

        }
    }

    @Nested
    @DisplayName("주문 취소")
    class cancelOrder {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");
            Delivery delivery = new Delivery("서대문구");
            OrderItem orderItem = OrderItem.createOrderItem(item, 10);
            Order order = Order.createOrder(member, delivery, orderItem);

            when(orderRepository.findById(order.getId()))
                    .thenReturn(Optional.of(order));

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> orderService.cancelOrder(order.getId()));
        }

        @DisplayName("실패 - 존재하지 않는 주문")
        @Test
        void failByOrderId() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");
            Delivery delivery = new Delivery("서대문구");
            OrderItem orderItem = OrderItem.createOrderItem(item, 10);
            Order order = Order.createOrder(member, delivery, orderItem);

            //when
            //then
            assertThatThrownBy(() -> orderService.cancelOrder(order.getId()))
                    .isExactlyInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("주문 조회 - 여러개")
    class findOrders {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");

            when(memberRepository.findById("tnwls"))
                    .thenReturn(Optional.of(member));
            when(orderRepository.findByMemberId(member.getId(), PageRequest.of(0, 1)))
                    .thenReturn(new ArrayList<>());
            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> orderService.findOrders(member.getId(), PageRequest.of(0, 1)));
        }

        @DisplayName("실패 - 존재하지 않는 회원")
        @Test
        void failByMemberId() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");

            //when
            //then
            assertThatThrownBy(() -> orderService.findOrders(member.getId(), PageRequest.of(0, 1)))
                    .isExactlyInstanceOf(NotFoundException.class);
        }
    }

    @Nested
    @DisplayName("주문 조회 - 1개")
    class findOrder {

        @DisplayName("성공")
        @Test
        void success() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");
            Delivery delivery = new Delivery("서대문구");
            OrderItem orderItem = OrderItem.createOrderItem(item, 10);
            Order order = Order.createOrder(member, delivery, orderItem);

            when(orderRepository.findById(order.getId()))
                    .thenReturn(Optional.of(order));

            //when
            //then
            assertThatNoException()
                    .isThrownBy(() -> orderService.findById(order.getId()));

        }

        @DisplayName("실패 - 존재하지 않는 주문")
        @Test
        void failByOrderId() {
            //given
            Member member = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
            Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");
            Delivery delivery = new Delivery("서대문구");
            OrderItem orderItem = OrderItem.createOrderItem(item, 10);
            Order order = Order.createOrder(member, delivery, orderItem);

            //when
            //then
            assertThatThrownBy(() -> orderService.findById(order.getId()))
                    .isExactlyInstanceOf(NotFoundException.class);
        }
    }
}