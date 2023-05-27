package NovelNetwork.NovelNetwork.Repository;

import NovelNetwork.NovelNetwork.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPassword(String password);
    User findByUsername(String username);
    User findByIdAndPassword(String id, String password);

    User findById(String id);

    User findByEmail(String email);

    Boolean existsById(String id);

}
