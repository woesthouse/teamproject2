package NovelNetwork.NovelNetwork.Service;

import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean isUserExisting(String id, String password) {
        User user = userRepository.findByIdAndPassword(id, password);
        return user != null;
    }

    public User loginUser(String id, String password) {
        return userRepository.findByIdAndPassword(id, password);
    }
}
