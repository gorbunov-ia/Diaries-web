package ru.gorbunov.diaries.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.gorbunov.diaries.domain.Role;

/**
 *
 * @author Gorbunov.ia
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer>{    
}