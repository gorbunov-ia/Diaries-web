package ru.gorbunov.diaries.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ru.gorbunov.diaries.domain.Note;

/**
 *
 * @author Gorbunov.ia
 */
@Repository
public interface NoteRepository extends CrudRepository<Note, Integer>, JpaSpecificationExecutor<Note> {           
}
