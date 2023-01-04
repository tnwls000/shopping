package web.clone.onemorebag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.clone.onemorebag.entity.ItemLike;

import java.util.Optional;

@Repository
public interface ItemLikeRepository extends JpaRepository<ItemLike, Long> {
    Optional<ItemLike> findByMemberIdAndItemId(String memberId, Long itemId);
    void deleteByMemberIdAndItemId(String memberId, Long itemId);
}
