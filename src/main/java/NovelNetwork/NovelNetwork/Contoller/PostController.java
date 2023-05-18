package NovelNetwork.NovelNetwork.Contoller;


import NovelNetwork.NovelNetwork.Domain.Post;
import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Service.PostService;
import NovelNetwork.NovelNetwork.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/noticeBoard")
    public String noticeBoardPage(Model model)
    {
        List<Post> postlist = postService.getAllPost();
        model.addAllAttributes(postlist);
        return "noticeBoard";
    }

    @GetMapping("/createPost")
    public String createPost(HttpSession session, Model model) {
        User user = (User)session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("post", new Post()); // Post 객체 추가
            return "createPost";
        } else {
            model.addAttribute("errorMessage", "You must be logged in to create a post.");
            return "home";
        }
    }



    @PostMapping("/board/new")
    public String newPost(@ModelAttribute Post post, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {  // 사용자가 로그인한 상태인지 확인
            return "redirect:/home";  // 로그인 페이지로 리다이렉트
        }
        post.setUserId(user.getUserNumber());  // Post 객체에 사용자 정보 추가
        post.setWriter(user.getNickname());
        postService.addPost(post);  // Post 저장
        return "redirect:/noticeBoard";  // 게시판 페이지로 리다이렉트
    }



}
