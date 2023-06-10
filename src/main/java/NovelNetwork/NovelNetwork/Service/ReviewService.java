package NovelNetwork.NovelNetwork.Service;

import NovelNetwork.NovelNetwork.Domain.Book;
import NovelNetwork.NovelNetwork.Domain.Post;
import NovelNetwork.NovelNetwork.Domain.Review;
import NovelNetwork.NovelNetwork.Repository.BookRepository;
import NovelNetwork.NovelNetwork.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookService bookService, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }
    public void addReview(Review review, int bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        review.setBook(book);
        reviewRepository.save(review);
    }
    public List<Review> getAllReview() {return reviewRepository.findAll();}
    public Review findByReviewId(long reviewId) {return reviewRepository.findByReviewId(reviewId);}
    public Optional<Review> getReviewByReviewNumber(Long ReviewId) {
        return (reviewRepository.findByReviewId(ReviewId));
    }
    public void updateReview(Review existingReview) {
        reviewRepository.save(existingReview);
    }

    public boolean removeReview(Long reviewId){

        if ( reviewRepository.existsById(reviewId) ) {
            reviewRepository.deleteById(reviewId);
            return true;
        }
        else {
            return false;
        }
    }
    @Transactional
    public Review saveReviewPostWithBook(Review reviewPost, Integer bookId) {
        Optional<Book> book = bookService.findById(bookId);
        reviewPost.setBook(book.get());
        return reviewRepository.save(reviewPost);
    }

    // ... other service methods as needed
}


