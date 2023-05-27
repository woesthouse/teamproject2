package NovelNetwork.NovelNetwork.Contoller;

import NovelNetwork.NovelNetwork.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailVerificationController {
    @Autowired
    private EmailService emailService;  // 이메일 인증 서비스 주입

    @PostMapping("/requestVerificationCode")
    @ResponseBody
    public ResponseEntity<?> requestVerificationCode(@RequestParam String email) {
        try {
            emailService.sendVerificationCode(email);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/verifyCode")
    @ResponseBody
    public ResponseEntity<?> verifyCode(@RequestParam String email, @RequestParam String enteredCode) {
        if (emailService.verifyCode(email, enteredCode)) {
            return ResponseEntity.ok("true");
        } else {
            return ResponseEntity.ok("false");
        }
    }

}
