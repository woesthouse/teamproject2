package NovelNetwork.NovelNetwork.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NovelNetwork.NovelNetwork.Domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}

