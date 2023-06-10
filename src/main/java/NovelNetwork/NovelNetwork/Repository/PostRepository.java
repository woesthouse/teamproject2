package NovelNetwork.NovelNetwork.Repository;

import NovelNetwork.NovelNetwork.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();

    Optional<Post> findByPostNumber(long postNumber);

    List<Post> findByTitleContaining(String keyword);

    List<Post> findByWriterContaining(String keyword);

    List<Post> findByContentContaining(String keyword);
}
