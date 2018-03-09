package ru.gorbunov.diaries.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.gorbunov.diaries.domain.Role;

/**
 * Interface for generic CRUD operations with Roles of Users.
 *
 * @author Gorbunov.ia
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
}
