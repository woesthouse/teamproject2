package NovelNetwork.NovelNetwork.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import NovelNetwork.NovelNetwork.Domain.Book;
import NovelNetwork.NovelNetwork.Domain.Review;
import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private MockMvc mockMvc;
    private MockHttpSession session;
    private ObjectMapper objectMapper;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
        session = new MockHttpSession();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void whenReviewExists_thenReturnsReviewDetailPage() throws Exception {
        Review review = new Review(); // Initialize this with actual data
        given(reviewService.getReviewByReviewNumber(1L)).willReturn(Optional.of(review));

        mockMvc.perform(get("/reviews/{1}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("reviewDetail"));
    }

    @Test
    public void whenReviewDoesNotExist_thenReturnsErrorPage() throws Exception {
        given(reviewService.getReviewByReviewNumber(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/{1}/reviews", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    public void whenReviewServiceThrowsException_thenReturnsErrorPage() throws Exception {
        given(reviewService.getReviewByReviewNumber(1L)).willThrow(new RuntimeException());

        mockMvc.perform(get("/{1}/reviews", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }




    @Test
    public void showEditForm_withValidReviewAndUser_thenReturnEditReview() throws Exception {
        Long userId = 1L;
        User user = new User(); // Initialize this with actual data
        user.setUserNumber(userId);
        session.setAttribute("user", user);
        Review review = new Review(); // Initialize this with actual data
        review.setUserNumber(userId);
        given(reviewService.getReviewByReviewNumber(1L)).willReturn(Optional.of(review));

        mockMvc.perform(get("/post/edit/{1}", 1L).session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("editReview"));
    }


    @Test
    public void editPost_withValidReviewAndUser_thenReturnOk() throws Exception {
        Long userId = 1L;
        User user = new User(); // Initialize this with actual data
        user.setUserNumber(userId);
        session.setAttribute("user", user);
        Review review = new Review(); // Initialize this with actual data
        review.setUserNumber(userId);
        given(reviewService.getReviewByReviewNumber(1L)).willReturn(Optional.of(review));
        willDoNothing().given(reviewService).updateReview(any());

        mockMvc.perform(post("/reviewBoard/edit/{reviewId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review))
                        .session(session))
                .andExpect(status().isOk());
    }

    @Test
    public void 정상적인_상황_테스트() {
        Long reviewId = 1L;
        Review review = new Review();
        Book book = new Book();
        User user = new User();
        user.setUserNumber(1L);
        Review existingReview = new Review();
        existingReview.setUserNumber(1L);

        when(session.getAttribute("user")).thenReturn(user);
        when(reviewService.getReviewByReviewNumber(reviewId)).thenReturn(Optional.of(existingReview));
        doNothing().when(reviewService).updateReview(any(Review.class));

        ResponseEntity<String> response = reviewController.editPost(reviewId, review, session, book);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals("Review updated successfully!", response.getBody());
    }
}



