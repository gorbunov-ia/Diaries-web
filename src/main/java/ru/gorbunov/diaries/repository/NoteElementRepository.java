package ru.gorbunov.diaries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
}
