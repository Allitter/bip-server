package by.bip.site.service;

import by.bip.site.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findById(Long idUser);

    User findByLogin(String login);

    User create(User user);
}
