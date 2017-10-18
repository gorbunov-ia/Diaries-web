package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.domain.User;

public interface UserService {

    public User getUserByLogin(String login);

    public User getUser(Integer id);

    public User getUser();

}
