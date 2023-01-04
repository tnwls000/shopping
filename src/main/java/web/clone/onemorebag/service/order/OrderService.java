package web.clone.onemorebag.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.clone.onemorebag.entity.OrderItem;
import web.clone.onemorebag.entity.delivery.Delivery;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.entity.order.Order;
import web.clone.onemorebag.repository.item.ItemRepository;
import web.clone.onemorebag.repository.MemberRepository;
import web.clone.onemorebag.repository.OrderRepository;

import java.util.List;

import static web.clone.onemorebag.common.exception.api.ApiExceptionType.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    /**
     * 상품 주문
     */
    @Transactional
    public Long order(String memberId, Long itemId, String address, int count) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NOT_FOUND_ACCOUNT::getException);
        Item item = itemRepository.findById(itemId)
                .orElseThrow(NOT_FOUND_ITEM::getException);

        Delivery delivery = new Delivery(address);

        OrderItem orderItem = OrderItem.createOrderItem(item, count);
        Order order = Order.createOrder(member, delivery, orderItem);
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(NOT_FOUND_ORDER::getException);
        order.cancel();
    }

    /**
     * 주문 조회
     */
    public List<OrderDto> findOrders(String memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NOT_FOUND_ACCOUNT::getException);
        return OrderDto.of(orderRepository.findByMemberId(memberId, pageable));
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(NOT_FOUND_ORDER::getException);
    }
}
