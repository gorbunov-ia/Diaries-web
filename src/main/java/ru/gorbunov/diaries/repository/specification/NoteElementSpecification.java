package ru.gorbunov.diaries.repository.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.service.UserService;

/**
 *
 * @author Gorbunov.ia
 */
@Service
public final class NoteElementSpecification {
    
    private final UserService userService;
    
    public NoteElementSpecification(UserService userService) {
        this.userService = userService;        
    }

    public Specification<NoteElement> byUser() {
        return new Specification<NoteElement>() {            
            @Override
            public Predicate toPredicate(Root<NoteElement> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                User user = userService.getUser();
                
                if (user != null) {
                    
                    final Join<NoteElement, Note> joinNote = root.join("note");
                    
                    return cb.equal(joinNote.get("user"), user);
                } else {
                    return cb.disjunction();
                }                
            }
        };
    }
    
    public Specification<NoteElement> byNote(Integer id) {
        return new Specification<NoteElement>() {            
            @Override
            public Predicate toPredicate(Root<NoteElement> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {                
                return cb.equal(root.get("note"), id);
            }
        };
    }    
    
}
