package NovelNetwork.NovelNetwork.Controller;

import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping({"/home", "/"})
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
            return "logginedhome";
        }
        else {
            return "home";
        }
    }


    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute User user, Model model) {
        //User existingUser = userService.loginUser(user.getId(), user.getPassword());
        if (userService.isUserExisting(user.getId(), user.getPassword())) {
            return "signupFailed";
        } else {
            userService.addUser(user);
            System.out.println(user.getId() + user.getNickname());
            return "signupSuccess";
        }
    }



    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.loginUser(id, password);
        if (userService.isUserExisting(id, password)) {
            session.setAttribute("user", user);
            return "redirect:/logginedhome";
        } else {
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "loginfailed";
        }
    }


    @GetMapping("/logginedhome")
    public String logginedHomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "logginedhome";
    }

    @PostMapping("/logout")
    public String logoutHomepage(HttpSession session){
        session.invalidate();
        return "home";
    }





    @GetMapping("/signup_success")
    public String signupSuccessPage() {
        return "signupsuccess";
    }



}
