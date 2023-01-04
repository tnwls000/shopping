package web.clone.onemorebag.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.clone.onemorebag.entity.item.Item;
import web.clone.onemorebag.entity.member.Member;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "item_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemLike extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "item_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public ItemLike(Member member, Item item) {
        changeMember(member);
        changeItem(item);
    }

    //==연관관계 메서드==
    public void changeMember(Member member) {
        this.member = member;
    }

    public void changeItem(Item item) {
        this.item = item;
    }
}
