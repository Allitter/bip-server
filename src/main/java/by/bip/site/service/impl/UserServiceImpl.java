package by.bip.site.service.impl;

import by.bip.site.exception.ResourceNotFoundException;
import by.bip.site.exception.UserAlreadyExistsException;
import by.bip.site.model.User;
import by.bip.site.model.UserRole;
import by.bip.site.repository.UserRepository;
import by.bip.site.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public User create(User user) {
        String login = user.getLogin();
        if (isLoginUsed(login)) {
            String message = String.format("User with login: [%s] already exists", login);
            throw new UserAlreadyExistsException(message);
        }

        user.setId(null);
        user.setEnabled(true);
        user.setRole(UserRole.USER);
        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    private boolean isLoginUsed(String login) {
        return userRepository
                .findByLogin(login)
                .isPresent();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByLogin(username);
    }
}
