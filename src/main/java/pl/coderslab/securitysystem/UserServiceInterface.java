package pl.coderslab.securitysystem;

public interface UserServiceInterface {
    User findByUserName(String name);
    void saveUser(User user);

    void saveAdmin(User admin);
}
