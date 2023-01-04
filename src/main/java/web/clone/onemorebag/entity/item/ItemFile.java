package web.clone.onemorebag.entity.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.clone.onemorebag.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemFile extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "item_file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //양방향
    @JoinColumn(name = "item_id")
    private Item item;

    private String uploadFileName;
    private String storeFileName;
    private String extension;

    public ItemFile(String uploadFileName, String storeFileName, String extension) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.extension = extension;
    }

    //==연관관계 메서드==
    protected void setItem(Item item) {
        this.item = item;
    }

    public String getOriginalFileName() {
        return storeFileName + "." + extension;
    }

}
