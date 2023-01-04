package web.clone.onemorebag.common.exception.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.clone.onemorebag.common.exception.CustomException;
import web.clone.onemorebag.common.exception.api.httpstatusexception.BadRequestException;
import web.clone.onemorebag.common.exception.api.httpstatusexception.ForbiddenException;
import web.clone.onemorebag.common.exception.api.httpstatusexception.NotFoundException;

@Getter
@RequiredArgsConstructor
public enum ApiExceptionType {

    NOT_FOUND_ACCOUNT(new NotFoundException("해당 계정이 존재하지 않습니다")),
    REQUIRED_ACCOUNT_ID(new BadRequestException("회원 ID는 필수입니다")),
    FORBIDDEN_ACCOUNT(new ForbiddenException("해당 접근 권한이 없습니다")),

    NOT_FOUND_ITEM(new NotFoundException("해당 상품이 존재하지 않습니다")),
    REQUIRED_ITEM_ID(new BadRequestException("상품의 고유 번호는 필수입니다")),

    NOT_FOUND_ORDER(new NotFoundException("해당 주문이 존재하지 않습니다")),
    FORBIDDEN_ORDER(new ForbiddenException("해당 주문에 대한 접근 권한이 없습니다")),

    NOT_FOUND_ITEM_LIKE(new NotFoundException("해당 좋아요가 존재하지 않습니다")),

    NOT_FOUND_SEARCH(new NotFoundException("일치하는 검색 결과가 없습니다")),
    ;

    private final CustomException exception;
}
