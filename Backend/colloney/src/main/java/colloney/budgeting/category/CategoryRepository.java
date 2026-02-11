package colloney.budgeting.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndUser_Id(Long categoryId, Long userId);

    List<Category> findByUser_Id(Long userId);

}
