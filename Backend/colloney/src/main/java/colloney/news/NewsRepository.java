package colloney.news;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n.id FROM News n")
    List<Long> findAllIds();

    Page<News> findByRelatedIgnoreCase(String related, Pageable pageable);

    Page<News> findBySourceIgnoreCase(String source, Pageable pageable);

    Page<News> findAll(Pageable pageable);

    @Query("SELECT n FROM News n ORDER BY CASE WHEN LOWER(n.category) = 'top news' THEN 0 ELSE 1 END, n.datetime DESC")
    Page<News> findAllPrioritized(Pageable pageable);

    Page<News> findByCategoryIgnoreCase(String category, Pageable pageable);
}
