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

@Service
public final class NoteSpecification {    
       
    private final UserService userService;
    
    public NoteSpecification(UserService userService) {
        this.userService = userService;        
    }

    public Specification<Note> byUser() {
        return new Specification<Note>() {            
            @Override
            public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                User user = userService.getUser();
                
                if (user != null) {
                    return cb.equal(root.get("user"), user);
                } else {
                    return cb.disjunction();
                }                
            }
        };
    }

    public Specification<Note> byId(Integer id) {
        return new Specification<Note>() {            
            @Override
            public Predicate toPredicate(Root<Note> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {                
                return cb.equal(root.get("id"), id);
            }
        };
    }
    
}
