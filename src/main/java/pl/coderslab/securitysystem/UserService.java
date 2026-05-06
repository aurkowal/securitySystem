package pl.coderslab.securitysystem;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Secured("ROLE_SUPERADMIN")
    public List<String> getAdmins() {
        return List.of("Admin1", "Admin2", "Admin3");
    }
}
