package NovelNetwork.NovelNetwork.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postNumber;
    private String title;
    private String content;
    private String writer;

    private Long userId;


    public Post(String title, String content, String writer, Long userid) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.userId = userid;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getter, Setter, Constructor, equals, hashCode, toString 메서드
}
