package pl.coderslab.securitysystem;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {

    @GetMapping("/view")
    public String showInfo() {
        return "info dostępne dla wszystkich";
    }

    @Secured("ROLE_MODERATOR")
    @GetMapping("/update")
    public String updateInfo() {
        return "Zaktualizowano";
    }

    @Secured("ROLE_MODERATOR")
    @GetMapping("/remove")
    public String remove() {
        return "Usunięto";
    }


}
