package ru.loshmanov.service;

import ru.loshmanov.entity.User;

public interface UserService {

    User findByLogin(String login);

}
