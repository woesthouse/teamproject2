package NovelNetwork.NovelNetwork.Contoller;


import NovelNetwork.NovelNetwork.Domain.Post;
import NovelNetwork.NovelNetwork.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/search")
    public String Search(@RequestParam String search_txt,@RequestParam String type, Model model)
    {
        List<Post> postList = null;

        if(type == "title") {
            postList.addAll(postRepository.findByTitle(search_txt));
        }
        else if(type == "writer") {
            postList.addAll(postRepository.findByWriter(search_txt));
        }
        else if(type == "content"){
            postList.addAll(postRepository.findByContent(search_txt));
        }

        model.addAttribute(postList);

        return "searchResult";
    }
}
