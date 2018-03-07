package ru.gorbunov.diaries.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.gorbunov.diaries.domain.User;

import java.util.Optional;

/**
 * Interface for generic CRUD operations with User.
 *
 * @author Gorbunov.ia
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * Method get user by login.
     *
     * @param login user login in db
     * @return      user object
     */
    @EntityGraph(attributePaths = "roles")
    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = "roles")
    @Override
    Optional<User> findById(Integer id);
}
