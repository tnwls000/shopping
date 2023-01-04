package web.clone.onemorebag.common.exception.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.clone.onemorebag.common.exception.CustomException;
import web.clone.onemorebag.common.exception.form.item.DuplicateItemByIdException;
import web.clone.onemorebag.common.exception.form.item.DuplicateItemByNameException;
import web.clone.onemorebag.common.exception.form.item.EmptyFileException;
import web.clone.onemorebag.common.exception.form.order.AlreadyCancelOrderException;
import web.clone.onemorebag.common.exception.form.order.AlreadyCompDeliveryException;
import web.clone.onemorebag.common.exception.form.order.NotEnoughStockQuantity;
import web.clone.onemorebag.common.exception.form.signin.IncorrectPasswordException;
import web.clone.onemorebag.common.exception.form.signin.NotFoundAccountByIdException;
import web.clone.onemorebag.common.exception.form.signup.*;

@Getter
@RequiredArgsConstructor
public enum FormExceptionType {

    //회원가입 에러
    INVALID_MEMBERID(new InvalidMemberIdException("영문소문자 또는 영문소문자와 숫자를 조합한 4-16자만 가능합니다.", "memberId", "invalid")),
    INVALID_PASSWORD(new InvalidPasswordException("영문대소문자 및 숫자를 2가지 이상 조합한 10-16자만 가능합니다.", "password", "invalid")),
    INVALID_EMAIL(new InvalidEmailException("유효한 이메일을 입력해주세요.", "email", "invalid")),
    DUPLICATE_MEMBERID(new DuplicateMemberIdException("이미 존재하는 아이디입니다", "id", "duplicate")),

    //로그인 에러
    INCORRECT_PASSWORD(new IncorrectPasswordException("비밀번호가 일치하지 않습니다", "password", "incorrect")),
    NOT_FOUND_ACCOUNT_BY_ID(new NotFoundAccountByIdException("존재하지 않는 아이디입니다", "id", "incorrect")),

    //주문 에러
    NOT_ENOUGH_STOCKQUANTITY(new NotEnoughStockQuantity("상품 재고가 모자랍니다.", "stockQuantity", "denied")),
    ALREADY_COMP_DELIVERY(new AlreadyCompDeliveryException("이미 배송 완료된 상품은 취소가 불가능합니다", "deliveryStatus", "denied")),
    ALREADY_CANCEL_ORDER(new AlreadyCancelOrderException("이미 주문 취소된 상태입니다", "orderStatus", "denied")),

    //상품 에러
    DUPLICATE_ITEM_ID(new DuplicateItemByIdException("이미 존재하는 상품입니다", "id", "duplicate")),
    DUPLICATE_ITEM_NAME(new DuplicateItemByNameException("동일한 이름의 상품이 존재합니다", "name", "duplicate")),
    EMPTY_FILE(new EmptyFileException("파일을 하나 이상 첨부하세요", "files", "required")),
    ;
    private final CustomException exception;

}
