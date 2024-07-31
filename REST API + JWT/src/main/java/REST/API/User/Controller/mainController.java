package REST.API.User.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class mainController {
    @GetMapping("/main")
    public ResponseEntity<String> main() {
        return ResponseEntity.status(200).body("성공");
    }
}
