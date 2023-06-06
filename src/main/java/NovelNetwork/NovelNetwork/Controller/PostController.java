package NovelNetwork.NovelNetwork.Controller;

import NovelNetwork.NovelNetwork.Domain.Post;
import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/noticeBoard")
    public String noticeBoard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "noticeBoard";
    }


    @GetMapping("/createPost")
    public String createPost(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("post", new Post()); // Post 객체 추가
            return "createPost";
        } else {
            model.addAttribute("errorMessage", "You must be logged in to create a post.");
            return "home";
        }
    }

    @PostMapping("/board/new")
    public String newPost(@ModelAttribute Post post, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/home";
        }
        post.setUserNumber(user.getUserNumber());
        post.setWriter(user.getNickname());
        postService.addPost(post);

        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);

        return "redirect:/noticeBoard";
    }

    @GetMapping("/post/{postNumber}")
    public String postDetail(@PathVariable Long postNumber, Model model) {
        Optional<Post> optionalPost = postService.getPostByPostNumber(postNumber);
        if (optionalPost.isPresent()) {
            model.addAttribute("post", optionalPost.get());
            return "postDetail";
        } else {
            return "error";
        }
    }

    @GetMapping("/post/edit/{postNumber}")
    public String showEditForm(@PathVariable Long postNumber, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Optional<Post> optionalPost = postService.getPostByPostNumber(postNumber);

        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();
            if (existingPost.getUserNumber().equals(user.getUserNumber())) {
                model.addAttribute("post", existingPost);
                return "editPost";
            } else {
                return "error";
            }
        } else {
            return "error";
        }
    }

    @PostMapping("/post/edit/{postNumber}")
    public ResponseEntity<String> editPost(@PathVariable Long postNumber, @RequestBody Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");

        Optional<Post> optionalPost = postService.getPostByPostNumber(postNumber);
        if (!optionalPost.isPresent()) {
            return new ResponseEntity<>("No post found", HttpStatus.NOT_FOUND);
        }

        Post existingPost = optionalPost.get();
        if (!existingPost.getUserNumber().equals(user.getUserNumber())) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        postService.updatePost(existingPost);

        return new ResponseEntity<>("Post updated successfully!", HttpStatus.OK);
    }



    @DeleteMapping("/post/delete/{postNumber}")
    public ResponseEntity<String> deletePost(@PathVariable Long postNumber, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null || !postService.findByPostNumber(postNumber).getUserNumber().equals(user.getUserNumber())) {
            return new ResponseEntity<>("Error removing post", HttpStatus.BAD_REQUEST);
        }

        boolean isRemoved = postService.removePost(postNumber);
        if (isRemoved) {
            return new ResponseEntity<>("Post removed successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error removing post", HttpStatus.BAD_REQUEST);
        }
    }

}
