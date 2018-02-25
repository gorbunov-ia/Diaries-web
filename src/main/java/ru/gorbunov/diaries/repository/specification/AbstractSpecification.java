package ru.gorbunov.diaries.repository.specification;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import ru.gorbunov.diaries.domain.GeneralEntity;
import ru.gorbunov.diaries.domain.User;

/**
 * Specification for add condition to the entity into query to db.
 *
 * @param <T> domain entity
 * @author Gorbunov.ia
 */
public abstract class AbstractSpecification<T extends GeneralEntity> {

    /**
     * Create user condition to the query.
     *
     * @param user object
     * @return specification for query to the user
     */
    public abstract Specification<T> byUser(User user);

    /**
     * Create id condition to the query.
     *
     * @param id    domain id
     * @return      specification for query to the T
     */
    public Specification<T> byId(final Integer id) {
        return (root, cq, cb) -> cb.equal(root.get("id"), id);
    }

    /**
     * Method return Sort.Direction object for boolean sorting flag.
     *
     * @param isDesc sorting flag
     * @return sorting object
     */
    public Sort.Direction getDirection(boolean isDesc) {
        if (isDesc) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
}
