package web.clone.onemorebag.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.order.Order;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //단방향
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY) //양방향
    @JoinColumn(name = "order_id")
    private Order order;

    private int count;

    //==연관관계 메서드==
    public void changeItem(Item item) {
        this.item = item;
    }

    public void changeOrder(Order order) {
        this.order = order;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.changeItem(item);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    /** 주문 취소 */
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    /** 주문상품 전체 가격 조회 */
    public int getTotalPrice() {
        return getItem().getPrice() * getCount();
    }

}
