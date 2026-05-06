package pl.coderslab.securitysystem;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;
    private final UserServiceInterface userServiceInterface;

    public UserController(UserService userService, UserServiceInterface userServiceInterface) {
        this.userService = userService;
        this.userServiceInterface = userServiceInterface;
    }

    @GetMapping("/user/admins")
    @Secured("ROLE_SUPERADMIN")
    public List<String> showSuperAdminInfo() {
        return userService.getAdmins();
    }

    @GetMapping("/create-user")
    public String createUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword("password");
        userServiceInterface.saveUser(user);
        return "user created";
    }

    @GetMapping("/create-admin")
    public String createAdmin() {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("password");
        userServiceInterface.saveAdmin(admin);
        return "admin created";
    }


    @GetMapping("/admin")
    public String adminPanel() {
        return "Witaj w panelu admina";
    }

    @GetMapping("/user")
    public String userPanel() {
        return "Witaj w panelu uzytkownika";
    }



}
