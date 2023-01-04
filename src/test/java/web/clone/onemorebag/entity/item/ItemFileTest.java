package web.clone.onemorebag.entity.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemFileTest {

    @DisplayName("저장된 파일 이름 조회")
    @Test
    void getOriginalFileName() {
        ItemFile file = new ItemFile("upload", "store", "png");
        String originalFileName = file.getOriginalFileName();
        Assertions.assertThat(originalFileName).isEqualTo("store.png");
    }
}