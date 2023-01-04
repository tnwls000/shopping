package web.clone.onemorebag.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import web.clone.onemorebag.common.auth.Login;
import web.clone.onemorebag.common.auth.LoginMember;
import web.clone.onemorebag.controller.item.ItemResponse;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.service.item.ItemService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    /**
     * 상품 목록 조회
     */
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> itemsPage(@PageableDefault Pageable pageable) {

        List<Item> items = itemService.getItems(pageable);

        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("items", items.stream().map(ItemResponse::from).collect(Collectors.toList()));
        return ResponseEntity.ok()
                .body(map);
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

}
