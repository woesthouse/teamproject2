package NovelNetwork.NovelNetwork.Repository;

import NovelNetwork.NovelNetwork.Domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAll();

    Review findByReviewId(long reviewId);
    Optional<Review> findByReviewId(Long reviewId);

}


