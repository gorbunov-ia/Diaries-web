package ru.gorbunov.diaries.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.User;

/**
 * Specification for add condition to the notes into query to db.
 *
 * @author Gorbunov.ia
 */
@Service
public final class NoteSpecification {

    /**
     * Create user condition to the query.
     *
     * @param user object
     * @return specification for query to the user
     */
    public Specification<Note> byUser(User user) {
        return (root, cq, cb) -> {
            if (user != null) {
                return cb.equal(root.get("user"), user);
            } else {
                return cb.disjunction();
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
        return (root, cq, cb) -> cb.equal(root.get("id"), id);
    }

    /**
     * Method add "order by" statement to the query.
     *
     * @param field     field for sort
     * @param isDesc    asc or desc order
     * @return          specification for query to the note
     */
    public Specification<Note> orderBy(final String field, final boolean isDesc) {
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
