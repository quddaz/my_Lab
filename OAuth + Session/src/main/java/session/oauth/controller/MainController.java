package session.oauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import session.oauth.entity.CustomOAuth2User;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal CustomOAuth2User customOAuth2User, Model model) {
        if (customOAuth2User != null) {
            System.out.println("User: " + customOAuth2User.getUsername());
        } else {
            System.out.println("CustomOAuth2User is null");
        }

        model.addAttribute("customOAuth2User", customOAuth2User);
        return "title";
    }
}
