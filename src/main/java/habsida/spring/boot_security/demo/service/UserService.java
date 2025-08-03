package habsida.spring.boot_security.demo.service;


import habsida.spring.boot_security.demo.model.User;

public interface UserService {
    Object getAllUsers();

    void saveUser(User user);

    void deleteUserById(Long id);

    User getUserById(Long id);

    void updateUser(User user);
}
