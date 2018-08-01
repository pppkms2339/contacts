package ru.loshmanov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.loshmanov.entity.User;
import ru.loshmanov.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User findByLogin(String login) {
//        User user = new User();
//        user.setLogin("admin");
//        user.setPassword("admin");
        return userRepository.findByLogin(login);
    }

}
