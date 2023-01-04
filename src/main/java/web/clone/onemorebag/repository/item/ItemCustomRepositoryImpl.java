package web.clone.onemorebag.repository.item;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static web.clone.onemorebag.entity.item.QItem.item;
import static web.clone.onemorebag.entity.item.QItemFile.itemFile;

@RequiredArgsConstructor
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ItemSearchDto> searchItems(String name, Pageable pageable) {
        return queryFactory
                .select(new QItemSearchDto(
                        item.id,
                        item.name,
                        item.price,
                        item.stockQuantity,
                        item.category,
                        item.brand
                ))
                .from(item)
                .where(item.name.contains(name))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    /**
     * 1:N인 itemFile 따로 조회
     */
    @Override
    public List<ItemFileSearchDto> searchItemFiles(Long itemId) {
        return queryFactory
                .select(new QItemFileSearchDto(
                        itemFile.item.id,
                        itemFile.item.name,
                        itemFile.uploadFileName,
                        itemFile.storeFileName,
                        itemFile.extension
                ))
                .from(itemFile)
                .join(itemFile.item)
                .where(itemFile.item.id.eq(itemId))
                .fetch();

    }


}
