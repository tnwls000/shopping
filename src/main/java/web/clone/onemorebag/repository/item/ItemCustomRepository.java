package web.clone.onemorebag.repository.item;


import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemCustomRepository {

    List<ItemSearchDto> searchItems(String name, Pageable pageable);

    List<ItemFileSearchDto> searchItemFiles(Long itemId);
}
