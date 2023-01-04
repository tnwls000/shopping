package web.clone.onemorebag.controller.order;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
public class OrderSaveForm {

    @NotBlank(message = "itemId가 필요합니다")
    private Long itemId;

    @NotBlank(message = "주소를 입력해주세요")
    private String address;

    @NotBlank(message = "수량을 입력해주세요")
    @Size(min = 1, message = "수량을 1개 이상 입력해주세요")
    private int count;

}
