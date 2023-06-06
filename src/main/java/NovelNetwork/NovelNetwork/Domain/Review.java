package NovelNetwork.NovelNetwork.Domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime dateCreated;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userNumber", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;
    private int starRating;
    private String writer;
    @Column(insertable = false, updatable = false)
    private Long userNumber;


    public Review(Long reviewId, LocalDateTime dateCreated, User user, Book book, String content,
                  int starRating, String writer, Long userNumber) {
        this.reviewId = reviewId;
        this.dateCreated = dateCreated;
        this.user = user;
        this.book = book;
        this.content = content;
        this.starRating = starRating;
        this.writer = writer;
        this.userNumber = userNumber;
    }

    public Review() {
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public Book getBook() { return book; }

    public void setBook(Book book) {this.book = book;}

    public LocalDateTime getDateCreated() {return dateCreated;}

    public void setDateCreated(LocalDateTime dateCreated) {this.dateCreated = dateCreated;}

    public User getUser() {return user;}

    public void setUser(User user) {this.user = user;}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStarRating() {
        return starRating;
    }

    public void setStarRating(int starRating) {
        this.starRating = starRating;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Long userNumber) {
        this.userNumber = userNumber;
    }


    // Getter, Setter, Constructor, equals, hashCode, toString 메서드
}
