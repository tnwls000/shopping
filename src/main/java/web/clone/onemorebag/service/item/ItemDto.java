package web.clone.onemorebag.service.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import web.clone.onemorebag.common.file.FileDto;
import web.clone.onemorebag.entity.item.Category;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ItemDto {
    private String name;
    private int price;
    private int stockQuantity;
    private Category category;
    private String brand;
    private List<FileDto> fileDtos;
}
