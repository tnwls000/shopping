package web.clone.onemorebag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.clone.onemorebag.entity.ItemLike;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.repository.ItemLikeRepository;
import web.clone.onemorebag.repository.item.ItemRepository;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemLikeService {

    private final ItemRepository itemRepository;
    private final ItemLikeRepository itemLikeRepository;

    /**
     * 좋아요 되어 있는 객체 조회
     */
    public List<ItemLike> findItemLikes(String memberId, Pageable pageable) {
        List<Item> items = itemRepository.getItemsByOrderByIdDesc(pageable);
        return items.stream()
                .map(item -> itemLikeRepository.findByMemberIdAndItemId(memberId, item.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
