package web.clone.onemorebag.repository.item;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class ItemFileSearchDto {

    private Long itemId;
    private String itemName;
    private String uploadFileName;
    private String storeFileName;
    private String extension;

    @QueryProjection
    public ItemFileSearchDto(Long itemId, String itemName, String uploadFileName, String storeFileName, String extension) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }
}
