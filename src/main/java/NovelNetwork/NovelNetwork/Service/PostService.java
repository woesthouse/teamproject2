package NovelNetwork.NovelNetwork.Service;

import NovelNetwork.NovelNetwork.Domain.Post;
import NovelNetwork.NovelNetwork.Repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Post findByPostNumber(long postNumber) {
        return postRepository.findByPostNumber(postNumber)
                .orElse(null);
    }

    public Post getPostByPostNumber(Long postNumber) {
        return postRepository.findByPostNumber(postNumber)
                .orElse(null);
    }


    public void updatePost(Post existingPost) {
        postRepository.save(existingPost);
    }

    public boolean removePost(Long postNumber) {
        if (postRepository.existsById(postNumber)) {
            postRepository.deleteById(postNumber);
            return true;
        } else {
            return false;
        }
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
