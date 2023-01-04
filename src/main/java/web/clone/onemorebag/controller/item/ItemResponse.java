package web.clone.onemorebag.controller.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.item.ItemFile;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ItemResponse {

    private String name;
    private int price;
    private int stockQuantity;
    private Category category;
    private String brand;
    private List<String> filePaths;

    public static ItemResponse from(Item item) {
        return new ItemResponse(
                item.getName(),
                item.getPrice(),
                item.getStockQuantity(),
                item.getCategory(),
                item.getBrand(),
                getFilePaths(item));
    }

    private static List<String> getFilePaths(Item item) {
        return item.getItemFiles().stream()
                .map(ItemFile::getOriginalFileName)
                .collect(Collectors.toList());
    }
}
