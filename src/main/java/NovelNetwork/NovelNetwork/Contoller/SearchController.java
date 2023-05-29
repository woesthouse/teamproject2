package NovelNetwork.NovelNetwork.Contoller;


import NovelNetwork.NovelNetwork.Domain.Post;
import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/search")
    public String Search(@RequestParam String keyword, @RequestParam String type, Model model, HttpSession session)
    {
        List<Post> postList = new ArrayList<>();

        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }

        if(type.equals("title")) {
            postList.addAll(postRepository.findByTitleContaining(keyword));
        }
        else if(type.equals("writer")) {
            postList.addAll(postRepository.findByWriterContaining(keyword));
        }
        else if(type.equals("content")){
            postList.addAll(postRepository.findByContentContaining(keyword));
        }

        model.addAttribute("postList", postList);

        return "searchResult";
    }
}

