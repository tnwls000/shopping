package web.clone.onemorebag.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import web.clone.onemorebag.common.auth.Login;
import web.clone.onemorebag.common.auth.LoginMember;
import web.clone.onemorebag.controller.item.ItemResponse;
import web.clone.onemorebag.controller.member.MemberResponse;
import web.clone.onemorebag.entity.ItemLike;
import web.clone.onemorebag.entity.delivery.Delivery;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Grade;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.entity.order.Order;
import web.clone.onemorebag.entity.order.OrderStatus;
import web.clone.onemorebag.service.ItemLikeService;
import web.clone.onemorebag.service.item.ItemService;
import web.clone.onemorebag.service.member.MemberService;
import web.clone.onemorebag.service.order.OrderDto;
import web.clone.onemorebag.service.order.OrderService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;
import static web.clone.onemorebag.common.exception.api.ApiExceptionType.FORBIDDEN_ORDER;
import static web.clone.onemorebag.common.exception.api.ApiExceptionType.REQUIRED_ACCOUNT_ID;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/mypage")
public class MyPageApiController {

    private final MemberService memberService;
    private final OrderService orderService;
    private final ItemService itemService;
    private final ItemLikeService itemLikeService;

    /**
     * 회원 마이페이지 조회
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> myPage(@Login LoginMember loginMember) {
        Member member = findMember(loginMember);

        MyPageDto memberDto = new MyPageDto(member);
        List<MyPageOrderDto> orderDtos = member.getOrders().stream()
                .map(MyPageOrderDto::new)
                .collect(toList());
        Map<String, Object> map = new ConcurrentHashMap<>();
        map.put("memberDto", memberDto);
        map.put("orderDtos", orderDtos);
        return ResponseEntity.ok()
                .body(map);
    }

    /**
     * 주문 내역 조회
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> myOrder(@Login LoginMember loginMember,
                                                        Pageable pageable) {
        Member member = findMember(loginMember);
        List<OrderDto> orders = orderService.findOrders(loginMember.getMemberId(), pageable);
        return ResponseEntity.ok().body(orders);
    }

    /**
     * 주문 취소
     */
    @Transactional
    @DeleteMapping("/orders/{orderId}")
    public void cancelOrder(@Login LoginMember loginMember,
                            @PathVariable(required = false) Long orderId) {
        findMember(loginMember);
        validateOrderAccess(loginMember.getMemberId(), orderId);
        orderService.cancelOrder(orderId);
    }

    /**
     * 좋아요 목록 조회
     */
    @GetMapping("/likes")
    public ResponseEntity<List<ItemResponse>> findLikes(@Login LoginMember loginMember, Pageable pageable) {
        Member member = findMember(loginMember);
        List<ItemLike> itemLikes = itemLikeService.findItemLikes(loginMember.getMemberId(), pageable);
        List<Item> items = itemLikes.stream().map(ItemLike::getItem).collect(toList());
        return ResponseEntity.ok()
                .body(items.stream()
                        .map(ItemResponse::from)
                        .collect(toList()));
    }

    /**
     * 회원정보수정 조회
     */
    @GetMapping("/modify")
    public ResponseEntity<MemberResponse> updateMemberPage(@Login LoginMember loginMember) {
        Member member = findMember(loginMember);
        return ResponseEntity.ok()
                .body(MemberResponse.from(member));
    }

    private void validateOrderAccess(String memberId, Long orderId) {
        if (!memberId.equals(getOrderMemberId(orderId))) {
            throw FORBIDDEN_ORDER.getException();
        }
    }

    private String getOrderMemberId(Long orderId) {
        return orderService.findById(orderId).getMember().getId();
    }

    private Member findMember(LoginMember loginMember) {
        if (Objects.isNull(loginMember.getMemberId())) {
            throw REQUIRED_ACCOUNT_ID.getException();
        }
        return memberService.findMember(loginMember.getMemberId());
    }

    @Data
    static class MyPageDto {
        private String memberId;
        private String name;
        private Grade grade;

        public MyPageDto(Member member) {
            memberId = member.getId();
            name = member.getName();
        }
    }

    @Data
    static class MyPageOrderDto {
        private OrderStatus orderStatus;
        private Delivery delivery;

        public MyPageOrderDto(Order order) {
            orderStatus = order.getStatus();
            delivery = order.getDelivery();
        }
    }
}
