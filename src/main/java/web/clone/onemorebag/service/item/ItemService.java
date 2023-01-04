package web.clone.onemorebag.service.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.clone.onemorebag.common.file.FileDto;
import web.clone.onemorebag.entity.ItemLike;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.item.ItemFile;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.repository.ItemLikeRepository;
import web.clone.onemorebag.repository.MemberRepository;
import web.clone.onemorebag.repository.item.ItemFileSearchDto;
import web.clone.onemorebag.repository.item.ItemRepository;
import web.clone.onemorebag.repository.item.ItemSearchDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static web.clone.onemorebag.common.exception.api.ApiExceptionType.*;
import static web.clone.onemorebag.common.exception.form.FormExceptionType.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemLikeRepository itemLikeRepository;
    private final MemberRepository memberRepository;

    /**
     * 상품 등록(admin)
     */
    public Long addItem(ItemDto dto) {
        validateDuplicationByName(dto.getName());
        Item item = new Item(dto.getName(), dto.getPrice(), dto.getStockQuantity(), dto.getCategory(), dto.getBrand());
        validateDuplicationById(item.getId());
        dto.getFileDtos().forEach(file -> item.changeItemFile(
                new ItemFile(file.getUploadFileName(), file.getStoreFileName(), file.getExtension())
        ));
        itemRepository.save(item);
        return item.getId();
    }

    private void validateDuplicationByName(String name) {
        itemRepository.findByName(name).ifPresent(item -> {
            throw DUPLICATE_ITEM_NAME.getException();
        });
    }

    private void validateDuplicationById(Long id) {
        itemRepository.findById(id).ifPresent(item -> {
            throw DUPLICATE_ITEM_ID.getException();
        });
    }


    /**
     * 상품 조회(1개)
     */
    @Transactional(readOnly = true)
    public Item getItem(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(NOT_FOUND_ITEM::getException);
    }

    /**
     * 상품 조회(여러개)
     */
    @Transactional(readOnly = true)
    public List<Item> getItems(Pageable pageable) {
        return itemRepository.getItemsByOrderByIdDesc(pageable);
    }

    /**
     * 카테고리별 상품 조회
     */
    @Transactional(readOnly = true)
    public List<Item> getItemsByCategory(Category category, Pageable pageable) {
        return itemRepository.findByCategory(category, pageable);
    }

    /**
     * 상품 수정(admin)
     */
    public void updateItem(Long id, String name, int price, int stockQuantity, List<FileDto> fileDtos) {
        Item item = getItem(id);
        item.changeItemInfo(name, price, stockQuantity);
        fileDtos.forEach(file -> item.changeItemFile(
                new ItemFile(file.getUploadFileName(), file.getStoreFileName(), file.getExtension())
        ));
    }

    /**
     * 상품 삭제(admin)
     */
    public void deleteItem(Long id) {
        itemRepository.delete(getItem(id));
    }

    /**
     * 상품 검색
     */
    @Transactional(readOnly = true)
    public List<ItemSearchDto> searchItems(String name, Pageable pageable) {
        List<ItemSearchDto> itemSearchDtos = itemRepository.searchItems(name, pageable);
        if (itemSearchDtos.isEmpty()) {
            throw NOT_FOUND_SEARCH.getException();
        }
        //파일 dto 넣기
        for (ItemSearchDto itemSearchDto : itemSearchDtos) {
            List<ItemFileSearchDto> itemFileSearchDtos = itemRepository.searchItemFiles(itemSearchDto.getId());
            itemSearchDto.setItemFiles(itemFileSearchDtos);
        }

        return itemSearchDtos;
    }

    /**
     * 상품 좋아요 목록 조회
     */
    @Transactional(readOnly = true)
    public List<Integer> findLikes(String memberId, Pageable pageable) {
        List<Item> items = itemRepository.getItemsByOrderByIdDesc(pageable);
        List<Optional<ItemLike>> likeList = items.stream()
                .map(item -> itemLikeRepository.findByMemberIdAndItemId(memberId, item.getId()))
                .collect(Collectors.toList());
        return validateLikeEmpty(likeList);
    }

    private static List<Integer> validateLikeEmpty(List<Optional<ItemLike>> likeList) {
        List<Integer> result = new ArrayList<>();
        for (Optional<ItemLike> itemLike : likeList) {
            if (itemLike.isEmpty()) {
                result.add(0);
            }
            result.add(1);
        }
        return result;
    }

    /**
     * 상품 좋아요 조회
     */
    @Transactional(readOnly = true)
    public int findLike(String memberId, Long itemId) {
        Optional<ItemLike> itemLike = itemLikeRepository.findByMemberIdAndItemId(memberId, itemId);
        if (itemLike.isEmpty()) {
            return 0;
        }
        return 1;
    }

    /**
     * 상품 좋아요 기능
     */
    public int saveLike(String memberId, Long itemId) {
        Optional<ItemLike> findItemLike = itemLikeRepository.findByMemberIdAndItemId(memberId, itemId);
        if (findItemLike.isEmpty()) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(NOT_FOUND_ACCOUNT::getException);
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(NOT_FOUND_ITEM::getException);
            ItemLike itemLike = new ItemLike(member, item);
            itemLikeRepository.save(itemLike);
            return 1;
        }
        itemLikeRepository.deleteByMemberIdAndItemId(memberId, itemId);
        return 0;
    }
}
