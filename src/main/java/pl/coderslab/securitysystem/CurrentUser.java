package pl.coderslab.securitysystem;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {

    private final pl.coderslab.securitysystem.User user;

    public CurrentUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            pl.coderslab.securitysystem.User user
    ) {
        super(username, password, authorities);
        this.user = user;
    }

    public pl.coderslab.securitysystem.User getUser() {
        return user;
    }
}

