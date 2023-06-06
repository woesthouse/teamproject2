package NovelNetwork.NovelNetwork.Controller;

import NovelNetwork.NovelNetwork.Controller.ReviewController;
import NovelNetwork.NovelNetwork.Domain.Book;
import NovelNetwork.NovelNetwork.Domain.Review;
import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Service.BookService;
import NovelNetwork.NovelNetwork.Service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.mock.web.MockHttpSession;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


   public class ReviewControllerTest {

        private ReviewController controller;
        private BookService bookService;
        private ReviewService reviewService;
        private MockHttpSession session;
        private Model model;

        @BeforeEach
        public void setUp() {
            bookService = mock(BookService.class);
            reviewService = mock(ReviewService.class);
            controller = new ReviewController(bookService, reviewService);
            session = new MockHttpSession();
            model = mock(Model.class);
        }

        @Test
        public void testNoticeBoard() {
            // given
            User user = new User();
            Review review = new Review();
            when(reviewService.getAllReview()).thenReturn(Arrays.asList(review));

            // when
            session.setAttribute("user", user);
            String viewName = controller.noticeBoard(model, session);

            // then
            verify(reviewService, times(1)).getAllReview();
            assertEquals("reviewBoard", viewName);
        }

        @Test
        void testCreatePost() {
            // given
            User user = new User();
            Book book = new Book();
            when(bookService.findAll()).thenReturn(Arrays.asList(book));

            // when
            session.setAttribute("user", user);
            String viewName = controller.createPost(session, model);

            // then
            verify(bookService, times(1)).findAll();
            assertEquals("createReview", viewName);
        }

        @Test
        void testNewPost() {
            // given
            User user = new User();
            Review review = new Review();
            Book book = new Book();
            when(reviewService.getAllReview()).thenReturn(Arrays.asList(review));

            // when
            session.setAttribute("user", user);
            String viewName = controller.newPost(review, book, session, model);

            // then
            verify(reviewService, times(1)).addReview(review, book.getBookId());
            assertEquals("redirect:/reviewBoard", viewName);
        }

        @Test
        void testReviewDetail() {
            // given
            Long reviewId = 1L;
            Long bookId = 1L;
            Review review = new Review();
            when(reviewService.getReviewByReviewNumber(reviewId)).thenReturn(Optional.of(review));

            // when
            String viewName = controller.reviewDetail(reviewId, bookId, model);

            // then
            verify(reviewService, times(1)).getReviewByReviewNumber(reviewId);
            assertEquals("reviewDetail", viewName);
        }
    }


