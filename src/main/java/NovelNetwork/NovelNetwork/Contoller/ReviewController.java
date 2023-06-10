package NovelNetwork.NovelNetwork.Controller;

import NovelNetwork.NovelNetwork.Domain.Book;
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

        review.setUser(user);


        reviewService.addReview(review, book.getBookId());

        List<Review> reviews = reviewService.getAllReview();
        model.addAttribute("reviews", reviews);

        return "redirect:/reviewBoard";
    }

    @GetMapping("/reviews/{reviewId}")
    public String reviewDetail( @PathVariable Long reviewId, Model model) {
        Optional<Review> optionalReview = reviewService.getReviewByReviewNumber(reviewId);
        if (optionalReview.isPresent()) {
            model.addAttribute("review", optionalReview.get());
            return "reviewDetail";
        } else {
            return "error";
        }
    }


    @GetMapping("/review/edit/{reviewId}")
    public String showEditForm(@PathVariable Long reviewId, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Optional<Review> optionalReview = reviewService.getReviewByReviewNumber(reviewId);

        if (optionalReview.isPresent()) {
            Review existingReview = optionalReview.get();
            if (existingReview.getUserNumber().equals(user.getUserNumber())) {
                List<Book> books = bookService.findAll();
                model.addAttribute("books", books);
                model.addAttribute("review", existingReview);
                return "editReview";
            } else {
                return "error";
            }
        } else {
            return "error";
        }
    }

    @PostMapping("/reviewBoard/edit/{reviewId}")
    public ResponseEntity<String> editPost(@PathVariable Long reviewId, @RequestBody Review review, HttpSession session,
                                           Book book) {
        User user = (User) session.getAttribute("user");

        Optional<Review> optionalReview = reviewService.getReviewByReviewNumber(reviewId);
        if (!optionalReview.isPresent()) {
            return new ResponseEntity<>("No post found", HttpStatus.NOT_FOUND);
        }

        Review existingReview = optionalReview.get();
        if (!existingReview.getUserNumber().equals(user.getUserNumber())) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        Optional<Book> bookFromDb = bookService.findById(review.getBook().getBookId());
        existingReview.setBook(bookFromDb.get());

        //Book bookFromDb = bookService.getBook(book.getBookId());
        //existingReview.setBook(bookFromDb);

        //existingReview.setBook(book);

        existingReview.setStarRating(review.getStarRating());
        existingReview.setTitle(review.getTitle());
        existingReview.setContent(review.getContent());
        reviewService.updateReview(existingReview);

        return new ResponseEntity<>("Review updated successfully!", HttpStatus.OK);
    }


    @DeleteMapping("/review/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null || !reviewService.findByReviewId(reviewId).getUserNumber().equals(user.getUserNumber())) {
            return new ResponseEntity<>("Error removing review", HttpStatus.BAD_REQUEST);
        }

        boolean isRemoved = reviewService.removeReview(reviewId);
        if (isRemoved) {
            return new ResponseEntity<>("Review removed successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error removing Review", HttpStatus.BAD_REQUEST);
        }
    }
}



