package web.clone.onemorebag.controller.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter @Setter
public class ItemUpdateForm {

    @NotBlank(message = "상품 이름을 입력해주세요")
    @Size(max = 20, message = "상품 이름을 20자 내로 입력해주세요")
    private String name;

    @NotBlank(message = "상품 가격을 입력해주세요")
    @Size(max = 1000000, message = "상품 가격을 100,0000 이하로 입력해주세요")
    private int price;

    @NotBlank(message = "상품 수량을 입력해주세요")
    @Size(max = 1000, message = "상품 수량을 1,000 이하로 입력해주세요")
    private int stockQuantity;

    @NotBlank(message = "상품 파일을 첨부해주세요")
    private List<MultipartFile> files;
}
