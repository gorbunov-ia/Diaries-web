package ru.gorbunov.diaries.repository.specification;

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
     * Method add "order by" statement to the query.
     *
     * @param field     field for sort
     * @param isDesc    asc or desc order
     * @return          specification for query to the T
     */
    public Specification<T> orderBy(final String field, final boolean isDesc) {
        return (root, cq, cb) -> {
            if (isDesc) {
                cq.orderBy(cb.desc(root.get(field)));
            } else {
                cq.orderBy(cb.asc(root.get(field)));
            }
            return cb.conjunction();
        };
    }
}
