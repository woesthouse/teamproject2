package NovelNetwork.NovelNetwork.Contoller;

import NovelNetwork.NovelNetwork.Domain.User;
import NovelNetwork.NovelNetwork.Service.EmailService;
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

    @Autowired
    private EmailService emailService;


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
    public String signup(@RequestParam String id, @RequestParam String password,
                         @RequestParam String username, @RequestParam String nickname,
                         @RequestParam String email,@RequestParam String code, @RequestParam String confirm_password, Model model) {


        if (userService.findByEmail(email) != null) {
            model.addAttribute("errorMessage", "Email already exists.");
            return "signupFailed";
        }

        if (userService.findById(id) != null) {
            model.addAttribute("errorMessage", "ID already exists.");
            return "signupFailed";
        }

        User user = new User(id, username, password, nickname, email);
        userService.addUser(user);
        return "signupSuccess";
    }





    @PostMapping("/login")
    public String login(@RequestParam String id, @RequestParam String password, HttpSession session, Model model) {
        User user = userService.loginUser(id, password);
        if (user != null) {
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

    @GetMapping("/logout")
    public String getlogoutHomepage(HttpSession session){
        session.invalidate();
        return "home";
    }




    @GetMapping("/signup_success")
    public String signupSuccessPage() {
        return "signupsuccess";
    }


}
