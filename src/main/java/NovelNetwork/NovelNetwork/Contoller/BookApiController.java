package NovelNetwork.NovelNetwork.Contoller;

import NovelNetwork.NovelNetwork.Domain.Book;
import NovelNetwork.NovelNetwork.Domain.Review;
import NovelNetwork.NovelNetwork.Service.BookService;
import NovelNetwork.NovelNetwork.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookApiController {

    private final BookService bookService;
    private final ReviewService reviewService;

    @Autowired
    public BookApiController(BookService bookService, ReviewService reviewService) {
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer bookId) {
        return bookService.findById(bookId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer bookId, @RequestBody Book updatedBook) {
        return bookService.update(bookId, updatedBook)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer bookId) {
        return bookService.delete(bookId) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/{bookId}/reviews", method = RequestMethod.POST)
    public void addReview(@RequestBody Review newReview, @PathVariable int bookId) {
        reviewService.addReview(newReview, bookId);
    }
}