package web.clone.onemorebag.controller.item;

import lombok.Getter;
import web.clone.onemorebag.repository.item.ItemSearchDto;

import java.util.List;

@Getter
public class ItemSearchResponse {

    private List<ItemSearchDto> items;
    private int count;

    public ItemSearchResponse(List<ItemSearchDto> items) {
        this.items = items;
        count = items.size();
    }
}
