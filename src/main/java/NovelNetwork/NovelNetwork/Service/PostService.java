package NovelNetwork.NovelNetwork.Service;

import NovelNetwork.NovelNetwork.Domain.Post;
import NovelNetwork.NovelNetwork.Repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPost()
    {
       return postRepository.findAll();
    }

    public void addPost(Post post) {
        postRepository.save(post);
    }
}
