package NovelNetwork.NovelNetwork.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.jetbrains.annotations.NotNull;

@Entity
@NotNull
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNumber;
    private String title;
    private String content;
    private String writer;

    private Long userNumber;


    public Post(String title, String content, String writer, Long userNumber) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.userNumber = userNumber;
    }

    public Post() {
    }

    public Long getId() {
        return postNumber;
    }

    public void setId(Long id) {
        this.postNumber = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Long getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(Long postNumber) {
        this.postNumber = postNumber;
    }

    public Long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(Long userNumber) {
        this.userNumber = userNumber;
    }

    // Getter, Setter, Constructor, equals, hashCode, toString 메서드
}
