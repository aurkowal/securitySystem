package pl.coderslab.securitysystem;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @GetMapping("/success")
    public String success() {
        return "Zalogowano poprawnie ✅";
    }

    @GetMapping("/whoami")
    public Object whoami(Authentication auth) {
        return auth;
    }


    @GetMapping("/all")
    public String all() {
        return "Dostęp dla wszystkich ✅";
    }

    @GetMapping("/restricted")
    public String restricted() {
        return "Dostęp tylko po zalogowaniu 🔒";
    }



}
