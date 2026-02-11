package colloney.ai;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiRepository extends JpaRepository<AiChat, Long> {

    List<AiChat> findByUser_IdOrderByCreatedAtDesc(Long userId);

    void deleteByUserId(Long userId);
}
