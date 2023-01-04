package web.clone.onemorebag.service.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import web.clone.onemorebag.common.WebConfig;
import web.clone.onemorebag.entity.OrderItem;
import web.clone.onemorebag.entity.delivery.Delivery;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.entity.order.Order;
import web.clone.onemorebag.repository.OrderRepository;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
@Import(WebConfig.class)
public class OrderFindServiceTest {

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Transactional
    @Test
    void findOrders() {
        Member member1 = new Member("tnwls", "tnwls123456", "tnwlssla20@naver.com", "박수진");
        Member member2 = new Member("tnwls1", "tnwls123456", "tnwlssla20@naver.com", "박수진");
        Item item = new Item("볼펜", 1000, 100, Category.ACC, "최고심");
        Delivery delivery = new Delivery("서대문구");
        OrderItem orderItem = OrderItem.createOrderItem(item, 10);

        for (int i = 0; i < 10; i++) {
            Order order = Order.createOrder(member1, new Delivery("서대문구" + i), orderItem);
            em.persist(order);
            orderRepository.save(order);
        }
        Order order22 = Order.createOrder(member2, delivery, orderItem);
        em.persist(member1);
        em.persist(member2);
        em.persist(item);
        em.persist(delivery);
        em.persist(orderItem);
        em.persist(order22);
        orderRepository.save(order22);

        em.flush();
        em.clear();


        List<OrderDto> orders = orderService.findOrders(member1.getId(), PageRequest.of(0, 10));
        for (OrderDto order : orders) {
            System.out.println("order address = " + order.getDelivery().getAddress());
        }

    }
}
