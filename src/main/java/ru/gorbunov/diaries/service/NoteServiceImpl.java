package ru.gorbunov.diaries.service;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;

import java.util.List;

/**
 * Implementation of service for interaction with notes.
 *
 * @author Gorbunov.ia
 */
@Service
public class NoteServiceImpl implements NoteService {

    /**
     * Repository for Note.
     */
    private final NoteRepository noteRepository;

    /**
     * Specification for Note.
     */
    private final NoteSpecification noteSpecification;

    /**
     * Base constructor.
     *
     * @param noteRepository     repository for crud operation with db
     * @param noteSpecification  specification for add condition into query to db
     */
    public NoteServiceImpl(final NoteRepository noteRepository,
                           final NoteSpecification noteSpecification) {
        this.noteRepository = noteRepository;
        this.noteSpecification = noteSpecification;
    }

    @Override
    public List<Note> getUserNotesWithSort(String field, boolean isDesc) {
        return noteRepository.findAll(Specifications.where(noteSpecification.byUser())
                .and(noteSpecification.orderBy(field, isDesc)));
    }

    @Override
    public Note getUserNoteById(Integer noteId) {
        return noteRepository.findOne(Specifications.where(noteSpecification.byUser())
                .and(noteSpecification.byId(noteId)));
    }
}
