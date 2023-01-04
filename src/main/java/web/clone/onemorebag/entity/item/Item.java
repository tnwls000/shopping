package web.clone.onemorebag.entity.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.clone.onemorebag.entity.BaseEntity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static web.clone.onemorebag.common.exception.form.FormExceptionType.NOT_ENOUGH_STOCKQUANTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String brand;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true) //양방향
    private List<ItemFile> itemFiles = new ArrayList<>();

    public Item(String name, int price, int stockQuantity, Category category, String brand) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.brand = brand;
    }

    public void changeItemInfo(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //==연관관계 메서드==
    public void changeItemFile(ItemFile itemFile) {
        itemFiles.add(itemFile);
        itemFile.setItem(this);
    }

    //==비즈니스 로직==
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        int restStock = stockQuantity - quantity;

        if (restStock < 0) {
            throw NOT_ENOUGH_STOCKQUANTITY.getException();
        }
        this.stockQuantity = restStock;
    }


}
