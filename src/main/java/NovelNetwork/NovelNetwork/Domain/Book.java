package NovelNetwork.NovelNetwork.Domain;

import jakarta.persistence.*;


@Entity
@Table(name = "book")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private String title;

    private String author;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Lob
    private byte[] coverImage;

    private float rating;




public Integer getBookId() {
    return bookId;
}

public void setBookId(Integer bookId) {
    this.bookId = bookId;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getAuthor() {
    return author;
}

public void setAuthor(String author) {
    this.author = author;
}

public String getContents() {
    return contents;
}

public void setContents(String contents) {
    this.contents = contents;
}

public byte[] getCoverImage() {
    return coverImage;
}

public void setCoverImage(byte[] coverImage) {
    this.coverImage = coverImage;
}

public float getRating() {
    return rating;
}

public void setRating(float rating) {
    this.rating = rating;
}

}
