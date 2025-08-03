package habsida.spring.boot_security.demo.service;



import habsida.spring.boot_security.demo.model.User;
import habsida.spring.boot_security.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        Optional<User> userExists = userRepository.findByEmail(user.getEmail());
        if(userExists.isPresent()) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        Optional<User> userExists = userRepository.findByEmail(user.getEmail());
        if(userExists.isPresent() && !userExists.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException("No user with this id exists.");
        }
        userRepository.deleteById(id);
    }
}
