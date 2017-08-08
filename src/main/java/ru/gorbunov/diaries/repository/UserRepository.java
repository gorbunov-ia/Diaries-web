package ru.gorbunov.diaries.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.gorbunov.diaries.domain.User;

/**
 *
 * @author Gorbunov.ia
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    
    @EntityGraph(attributePaths = "roles")
    public User findOneByLogin(String login);
    
}
