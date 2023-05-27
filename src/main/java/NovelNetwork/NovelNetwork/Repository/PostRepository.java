package NovelNetwork.NovelNetwork.Repository;

import NovelNetwork.NovelNetwork.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAll();

    Post findByPostNumber(long postNumber);

    List<Post> findByTitle(String title);

    List<Post> findByWriter(String writer);

    @Query("SELECT p FROM Post p WHERE p.content LIKE %:search_txt%")
    List<Post> findByContent(@Param("search_txt") String search_txt);
}
