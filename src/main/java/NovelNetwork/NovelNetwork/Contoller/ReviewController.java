package NovelNetwork.NovelNetwork.Contoller;

import NovelNetwork.NovelNetwork.Domain.Book;
import NovelNetwork.NovelNetwork.Domain.Post;
import NovelNetwork.NovelNetwork.Domain.Review;
import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Service.BookService;
import NovelNetwork.NovelNetwork.Service.ReviewService;
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
public class ReviewController {

    private final BookService bookService;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }
    @GetMapping("/reviewBoard")
    public String noticeBoard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        List<Review> reviews = reviewService.getAllReview();
        model.addAttribute("posts", reviews);
        return "reviewBoard";
    }
    @GetMapping("/createReview")
    public String createPost(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Book> books = bookService.findAll();
            model.addAttribute("books", books);
            model.addAttribute("user", user);
            model.addAttribute("review", new Review()); // Review 객체 추가
            return "createReview";
        } else {
            model.addAttribute("errorMessage", "You must be logged in to create a post.");
            return "home";
        }
    }
    @PostMapping("/reviewBoard/new")
    public String newPost(@ModelAttribute Review review, Book book, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/home";
        }
        review.setUserNumber(user.getUserNumber());
        review.setWriter(user.getNickname());

        int bookId = book.getBookId();
        reviewService.addReview(review, bookId);

        List<Review> reviews = reviewService.getAllReview();
        model.addAttribute("reviews", reviews);

        return "redirect:/reviewBoard";
    }

    @GetMapping("/{bookId}/reviews")
    public String reviewDetail(@PathVariable Long reviewId, Model model) {
        Optional<Review> optionalReview = reviewService.getReviewByReviewNumber(reviewId);
        if (optionalReview.isPresent()) {
            model.addAttribute("review", optionalReview.get());
            return "reviewDetail";
        } else {
            return "error";
        }
    }
/*
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
*/
}



