package ru.gorbunov.diaries.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.service.UserService;

/**
 * Specification for add condition to the notes into query to db.
 *
 * @author Gorbunov.ia
 */
@Service
public final class NoteSpecification {

    /**
     * Service for interaction with user.
     */
    private final UserService userService;

    /**
     * Base constructor.
     *
     * @param service service for interaction with user
     */
    public NoteSpecification(final UserService service) {
        this.userService = service;
    }

    /**
     * Create user condition to the query.
     *
     * @return specification for query to the user
     */
    public Specification<Note> byUser() {
        return new Specification<Note>() {
            @Override
            public Predicate toPredicate(final Root<Note> root, final CriteriaQuery<?> cq, final CriteriaBuilder cb) {
                User user = userService.getUser();

                if (user != null) {
                    return cb.equal(root.get("user"), user);
                } else {
                    return cb.disjunction();
                }
            }
        };
    }

    /**
     * Create note id condition to the query.
     *
     * @param id    user id
     * @return      specification for query to the note
     */
    public Specification<Note> byId(final Integer id) {
        return new Specification<Note>() {
            @Override
            public Predicate toPredicate(final Root<Note> root, final CriteriaQuery<?> cq, final CriteriaBuilder cb) {
                return cb.equal(root.get("id"), id);
            }
        };
    }

    /**
     * Method add "order by" statement to the query.
     *
     * @param field     field for sort
     * @param isDesc    asc or desc order
     * @return          specification for query to the note
     */
    public Specification<Note> orderBy(final String field, final boolean isDesc) {
        return new Specification<Note>() {
            @Override
            public Predicate toPredicate(final Root<Note> root, final CriteriaQuery<?> cq, final CriteriaBuilder cb) {
                if (isDesc) {
                    cq.orderBy(cb.desc(root.get(field)));
                } else {
                    cq.orderBy(cb.asc(root.get(field)));
                }
                return cb.conjunction();
            }
        };
    }

}
