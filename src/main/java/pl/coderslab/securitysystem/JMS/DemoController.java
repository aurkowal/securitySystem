package pl.coderslab.securitysystem.JMS;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/all-users")
    public String allAccess() {
        return "Dostęp do wszystkich użytkowników.";
    }

    @GetMapping("/restrictedLogin")
    public String restrictedAccess() {
        return "Dostęp ograniczony do zalogowanych użytkowników.";
    }

    @GetMapping("/restrictedAdmin")
    @Secured({"ROLE_ADMIN"})
    public String restrictedAdminAccess() {
        return "Dostęp ograniczony do zalogowanych użytkowników.";
    }
}
