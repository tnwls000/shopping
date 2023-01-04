package web.clone.onemorebag.repository.item;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web.clone.onemorebag.entity.item.Category;
import web.clone.onemorebag.entity.item.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemCustomRepository {
    List<Item> getItemsByOrderByIdDesc(Pageable pageable);
    Optional<Item> findByName(String name);
    List<Item> findByCategory(Category category, Pageable pageable);

}
