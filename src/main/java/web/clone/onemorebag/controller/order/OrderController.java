package web.clone.onemorebag.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.clone.onemorebag.common.auth.Login;
import web.clone.onemorebag.common.auth.LoginMember;
import web.clone.onemorebag.common.exception.form.CustomFormException;
import web.clone.onemorebag.service.order.OrderService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    /**
     * 상품 주문
     */
    @GetMapping("/{itemId}")
    public String orderPage(@Login LoginMember loginMember,
                            @PathVariable(required = false) Long itemId,
                            OrderSaveForm form) {
        form.setItemId(itemId);
        return "order";
    }

    @Transactional
    @PostMapping("/{itemId}")
    public String order(@Login LoginMember loginMember,
                        @Valid OrderSaveForm form, Model model,
                        BindingResult bindingResult) throws IOException {
        try {
            if (!bindingResult.hasFieldErrors()) {
                orderService.order(loginMember.getMemberId(), form.getItemId(), form.getAddress(), form.getCount());
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getMessage(), e.getField(), e.getErrorCode());
        }

        if (bindingResult.hasErrors()) {
            //난중에 추가
            model.addAttribute("OrderSaveForm", new OrderSaveForm());
            model.addAttribute("loginMember", loginMember);
            return "order";
        }
        return "redirect:/mypage/orders";
    }
}
