package ru.gorbunov.diaries.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.gorbunov.diaries.domain.NoteElement;

/**
 *
 * @author Gorbunov.ia
 */
@Repository
public interface NoteElementRepository extends CrudRepository<NoteElement, Integer>, JpaSpecificationExecutor<NoteElement> {    
}
