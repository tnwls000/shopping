package web.clone.onemorebag.repository.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import web.clone.onemorebag.entity.item.Category;

import java.util.List;

@Data
public class ItemSearchDto {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private Category category;
    private String brand;
    private List<ItemFileSearchDto> itemFiles;

    @QueryProjection
    public ItemSearchDto(Long id, String name, int price, int stockQuantity, Category category, String brand) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.brand = brand;
    }
}
