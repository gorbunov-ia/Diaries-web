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
public final class NoteSpecification extends AbstractSpecification<Note> {

    /**
     * Create user condition to the query.
     *
     * @param user object
     * @return specification for query to the user
     */
    @Override
    public Specification<Note> byUser(User user) {
        return (root, cq, cb) -> {
            if (user != null) {
                return cb.equal(root.get("user"), user);
            } else {
                return cb.disjunction();
            }
        };
    }

}
