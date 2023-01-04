package web.clone.onemorebag.entity.delivery;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.clone.onemorebag.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private String address;

    public Delivery(String address) {
        this.address = address;
        this.status = DeliveryStatus.READY;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
