package web.clone.onemorebag.controller.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import web.clone.onemorebag.common.auth.Login;
import web.clone.onemorebag.common.auth.LoginMember;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Grade;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.service.item.ItemService;
import web.clone.onemorebag.service.member.MemberService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static web.clone.onemorebag.common.exception.api.ApiExceptionType.*;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/item")
public class ItemApiController {

    private final MemberService memberService;
    private final ItemService itemService;

    /**
     * 상품 목록 조회
     */
    @GetMapping("")
    public ResponseEntity<Map<String, Object>> itemsPage(@Login LoginMember loginMember,
                                                         @PageableDefault Pageable pageable) {

        Optional<String> memberId = findMemberId(loginMember);
        List<Item> items = itemService.getItems(pageable);
        List<Integer> likes = getLikes(loginMember, pageable, memberId);

        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("items", items.stream().map(ItemResponse::from).collect(Collectors.toList()));
        map.put("likes", likes);
        return ResponseEntity.ok()
                .body(map);
    }

    /**
     * 상품 상세 조회
     */
    @GetMapping("/{itemId}")
    public ResponseEntity<Map<String, Object>> getItem(@Login LoginMember loginMember,
                                                @PathVariable(required = false) Long itemId) {
        if (Objects.isNull(itemId)) {
            REQUIRED_ITEM_ID.getException();
        }
        Optional<String> memberId = findMemberId(loginMember);
        Item item = itemService.getItem(itemId);

        int like = 0;
        if (memberId.isPresent()) {
            like = itemService.findLike(loginMember.getMemberId(), itemId);
        }

        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("item", ItemResponse.from(item));
        map.put("like", like);
        return ResponseEntity.ok()
                .body(map);
    }

    /**
     * 카테고리별 상품 조회
     */
    @GetMapping("/{category}")
    public ResponseEntity<Map<String, Object>> itemsPageByCategory(@Login LoginMember loginMember,
                                                                  @PathVariable("category") Category category,
                                                                  @PageableDefault Pageable pageable) {
        Optional<String> memberId = findMemberId(loginMember);
        List<Item> items = itemService.getItemsByCategory(category, pageable);
        List<Integer> likes = getLikes(loginMember, pageable, memberId);

        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("items", items.stream().map(ItemResponse::from).collect(Collectors.toList()));
        map.put("likes", likes);
        return ResponseEntity.ok()
                .body(map);

    }

    /**
     * 상품 좋아요
     */
    @Transactional
    @PostMapping("/{itemId}")
    public void likeItem(@Login LoginMember loginMember, @PathVariable(required = false) Long itemId) {
        if (loginMember == null) {
            throw REQUIRED_ACCOUNT_ID.getException();
        }
        if (Objects.isNull(itemId)) {
            REQUIRED_ITEM_ID.getException();
        }
        itemService.saveLike(loginMember.getMemberId(), itemId);
    }

    /**
     * 상품 검색
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchItems(
            @Login LoginMember loginMember,
            @RequestParam(defaultValue = "") String name,
            @PageableDefault Pageable pageable) {

        Optional<String> memberId = findMemberId(loginMember);
        List<Integer> likes = getLikes(loginMember, pageable, memberId);

        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("itemSearchResponse", new ItemSearchResponse(itemService.searchItems(name, pageable)));
        map.put("likes", likes);

        return ResponseEntity.ok().body(map);
    }

    private Optional<String> findMemberId(LoginMember loginMember) {
        return Optional.ofNullable(loginMember.getMemberId());
    }

    private List<Integer> getLikes(LoginMember loginMember, Pageable pageable, Optional<String> memberId) {
        List<Integer> likes = null;
        if (memberId.isPresent()) {
            likes = itemService.findLikes(loginMember.getMemberId(), pageable);
        } else {
            for (Integer like : likes) like = 0;
        }
        return likes;
    }

    /**
     * 상품 수정(admin)
     */
    @Transactional
    @GetMapping("/{itemId}/modify")
    public ResponseEntity<ItemResponse> updateItemPage(
                                 @Login LoginMember loginMember,
                                 @PathVariable(required = false) Long itemId) {
        if (Objects.isNull(itemId)) {
            throw REQUIRED_ITEM_ID.getException();
        }
        validateAccess(loginMember);
        Item findItem = itemService.getItem(itemId);
        return ResponseEntity.ok()
                .body(ItemResponse.from(findItem));
    }

    /**
     * 상품 삭제(admin)
     */
    @Transactional
    @DeleteMapping("/{itemId}/delete")
    public void deleteItem(@Login LoginMember loginMember, @PathVariable(required = false) Long itemId) {
        if (Objects.isNull(itemId)) {
            throw REQUIRED_ITEM_ID.getException();
        }
        validateAccess(loginMember);
        itemService.deleteItem(itemId);
    }

    private void validateAccess(LoginMember loginMember) {
        Member findMember = memberService.findMember(loginMember.getMemberId());
        if (!findMember.getGrade().equals(Grade.ADMIN)) {
            throw FORBIDDEN_ACCOUNT.getException();
        }
    }
}
