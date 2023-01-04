package web.clone.onemorebag.controller.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.clone.onemorebag.common.auth.Login;
import web.clone.onemorebag.common.auth.LoginMember;
import web.clone.onemorebag.common.exception.form.CustomFormException;
import web.clone.onemorebag.common.file.FileDto;
import web.clone.onemorebag.common.file.FileStore;
import web.clone.onemorebag.entity.member.Grade;
import web.clone.onemorebag.entity.member.Member;
import web.clone.onemorebag.service.item.ItemDto;
import web.clone.onemorebag.service.item.ItemService;
import web.clone.onemorebag.service.member.MemberService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static web.clone.onemorebag.common.exception.api.ApiExceptionType.FORBIDDEN_ACCOUNT;

@Controller
@RequiredArgsConstructor
@Transactional(readOnly = true)
@RequestMapping("/item")
public class ItemController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final FileStore fileStore;

    /**
     * 상품 등록(admin)
     */
    @GetMapping("/add")
    public String addItemPage(ItemSaveForm form, @Login LoginMember loginMember) {
        validateAccess(loginMember);
        return "item/add";
    }

    @Transactional
    @PostMapping("/add")
    public String addItem(@Valid ItemSaveForm form,
                          @Login LoginMember loginMember,
                          BindingResult bindingResult, Model model) throws IOException {
        validateAccess(loginMember);
        try {
            if (!bindingResult.hasFieldErrors()) {
                List<FileDto> fileDtos = fileStore.storeFiles(form.getFiles());
                itemService.addItem(new ItemDto(
                        form.getName(),
                        form.getPrice(),
                        form.getStockQuantity(),
                        form.getCategory(),
                        form.getBrand(),
                        fileDtos));
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getMessage(), e.getField(), e.getErrorCode());
        }

        if (bindingResult.hasErrors()) {
            //난중에 추가
            model.addAttribute("ItemSaveForm", new ItemSaveForm());
            model.addAttribute("loginMember", loginMember);
            return "item/add";
        }
        return "redirect:/item";
    }

    /**
     * 상품 수정(admin)
     */
    @Transactional
    @PostMapping("/{itemId}/modify")
    public String updateItem(@Valid ItemUpdateForm form,
                             @Login LoginMember loginMember,
                             @PathVariable Long itemId,
                             BindingResult bindingResult, Model model) throws IOException {
        try {
            if (!bindingResult.hasFieldErrors()) {
                List<FileDto> fileDtos = fileStore.storeFiles(form.getFiles());
                itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity(), fileDtos);
            }
        } catch (CustomFormException e) {
            bindingResult.rejectValue(e.getMessage(), e.getField(), e.getErrorCode());
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("loginMember", loginMember);
            return "item/modify";
        }

        return "redirect:/item";
    }

    private void validateAccess(LoginMember loginMember) {
        Member findMember = memberService.findMember(loginMember.getMemberId());
        if (!findMember.getGrade().equals(Grade.ADMIN)) {
            throw FORBIDDEN_ACCOUNT.getException();
        }
    }
}
