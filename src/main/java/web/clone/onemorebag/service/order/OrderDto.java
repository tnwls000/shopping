package web.clone.onemorebag.service.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import web.clone.onemorebag.entity.OrderItem;
import web.clone.onemorebag.entity.delivery.Delivery;
import web.clone.onemorebag.entity.order.Order;
import web.clone.onemorebag.entity.order.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class OrderDto {
    private OrderStatus status;
    private Delivery delivery;
    private List<OrderItem> orderItems;

    public static List<OrderDto> of(List<Order> orders) {
        return orders.stream().map(m -> {
            return new OrderDto(
                    m.getStatus(),
                    m.getDelivery(),
                    m.getOrderItems()
            );
        }).collect(Collectors.toList());
    }
}
