package ru.gorbunov.diaries.repository;

import org.springframework.data.repository.CrudRepository;

import ru.gorbunov.diaries.domain.User;

/**
 *
 * @author Gorbunov.ia
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    
    public User findByLogin(String login);
    
}
