package gods.work.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/view")
public class LoginViewController {

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "/signup";
    }

    @GetMapping("/index")
    public String index() {
        return "/main";
    }

}
