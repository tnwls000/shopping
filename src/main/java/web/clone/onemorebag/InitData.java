package web.clone.onemorebag;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import web.clone.onemorebag.entity.OrderItem;
import web.clone.onemorebag.entity.delivery.Delivery;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Grade;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.entity.order.Order;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

//@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        @PersistenceContext
        EntityManager em;

        public void init() {
            List<Member> members = createMembers();
            List<Item> items = createItems();
            createOrder(members, items);

            em.flush();
            em.clear();
        }

        private List<Member> createMembers() {
            List<Member> members = new ArrayList<>();

            Member member1 = new Member("tnwls", "tnwls24325235", "tnwls@naver.com", "박수진");
            Member member2 = new Member("tnwls1", "tnwls24325234", "tnwls@naver.com", "진수박");
            member2.setGrade(Grade.ADMIN);

            em.persist(member1);
            em.persist(member2);

            members.add(member1);
            members.add(member2);
            return members;
        }

        private List<Item> createItems() {
            List<Item> items = new ArrayList<>();

            Item item1 = new Item("최고심 인형", 1000, 100, Category.DOLL, "최고심");
            Item item2 = new Item("최고심 인형2", 2000, 200, Category.DOLL, "최고심");
            Item item3 = new Item("다이노 볼펜", 1000, 100, Category.STATIONERY, "다이노");
            Item item4 = new Item("다이노 가방", 2000, 200, Category.BAG, "다이노");
            Item item5 = new Item("생쥐 키링", 10000, 1000, Category.ACC, "생쥐");

            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(item4);
            em.persist(item5);

            items.add(item1);
            items.add(item2);
            items.add(item3);
            items.add(item4);
            items.add(item5);

            return items;
        }

        private List<Order> createOrder(List<Member> members, List<Item> items) {
            List<Order> orders = new ArrayList<>();

            Delivery delivery = new Delivery("서대문구");
            em.persist(delivery);
            OrderItem orderItem1 = OrderItem.createOrderItem(items.get(0), 10);
            OrderItem orderItem2 = OrderItem.createOrderItem(items.get(1), 10);
            em.persist(orderItem1);
            em.persist(orderItem2);

            Order order1 = Order.createOrder(members.get(0), delivery, orderItem1, orderItem2);
            Order order2 = Order.createOrder(members.get(0), delivery, orderItem1, orderItem2);
            em.persist(order1);
            em.persist(order2);
            orders.add(order1);
            orders.add(order2);

            return orders;
        }
    }

}
