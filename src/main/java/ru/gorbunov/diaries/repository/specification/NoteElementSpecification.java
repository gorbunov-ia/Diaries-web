package ru.gorbunov.diaries.repository.specification;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.domain.User;

/**
 * Specification for add condition to the note elements into query to db.
 *
 * @author Gorbunov.ia
 */
@Service
public final class NoteElementSpecification {

    /**
     * Create user condition to the query.
     *
     * @param user object
     * @return specification for query to the user
     */
    public Specification<NoteElement> byUser(User user) {
        return (root, cq, cb) -> {
            if (user != null) {
                final Join<NoteElement, Note> joinNote = root.join("note");
                return cb.equal(joinNote.get("user"), user);
            } else {
                return cb.disjunction();
            }
        };
    }

    /**
     * Create note id condition to the query.
     *
     * @param id    note id
     * @return      specification for query to the note
     */
    public Specification<NoteElement> byNote(final Integer id) {
        return (root, cq, cb) -> cb.equal(root.get("note"), id);
    }

    /**
     * Create note element id condition to the query.
     *
     * @param id    note element id
     * @return      specification for query to the note element
     */
    public Specification<NoteElement> byId(final Integer id) {
        return (root, cq, cb) -> cb.equal(root.get("id"), id);
    }

    /**
     * Create sort by condition to the query.
     *
     * @param sortBy    sort by
     * @return          specification for query to the note element
     */
    public Specification<NoteElement> bySortBy(final Integer sortBy) {
        return (root, cq, cb) -> cb.equal(root.get("sortBy"), sortBy);
    }

    /**
     * Create "between" sort by condition to the query.
     *
     * @param sortByFirst   minimal sort by value
     * @param sortByLast    maximum sort by value
     * @return              specification for query to the note element
     */
    public Specification<NoteElement> byRangeSortBy(final Integer sortByFirst,
                                                    final Integer sortByLast) {
        return (root, cq, cb) -> cb.and(cb.greaterThanOrEqualTo(root.get("sortBy"), sortByFirst),
                cb.lessThanOrEqualTo(root.get("sortBy"), sortByLast));
    }

    /**
     * Method add "order by" statement to the query.
     *
     * @param field     field for sort
     * @param isDesc    asc or desc order
     * @return          specification for query to the note element
     */
    public Specification<NoteElement> orderBy(final String field, final boolean isDesc) {
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
