package ru.gorbunov.diaries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.gorbunov.diaries.domain.NoteElement;

/**
 * Interface for generic CRUD operations with Note Elements.
 *
 * @author Gorbunov.ia
 */
@Repository
public interface NoteElementRepository extends JpaRepository<NoteElement, Integer>,
        JpaSpecificationExecutor<NoteElement> {

    /**
     * Get max note element sort by attribute for note.
     *
     * @param noteId note
     * @return max value of sort by
     */
    @Query("select max(ne.sortBy) from NoteElement ne where ne.note.id = :noteId")
    Integer getMaxSortByByNoteId(@Param("noteId") Integer noteId);

}
