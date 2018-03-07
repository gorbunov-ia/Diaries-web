package ru.gorbunov.diaries.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
     * Service for interaction with user.
     */
    private final UserService userService;

    /**
     * Base constructor.
     *
     * @param noteRepository    repository for crud operation with db
     * @param noteSpecification specification for add condition into query to db
     * @param userService       service for interaction with user
     */
    public NoteServiceImpl(final NoteRepository noteRepository,
                           final NoteSpecification noteSpecification,
                           final UserService userService) {
        this.noteRepository = noteRepository;
        this.noteSpecification = noteSpecification;
        this.userService = userService;
    }

    @Override
    public List<Note> getUserNotesWithSort(String field, boolean isDesc) {
        return userService.getUser()
                .map(user -> noteRepository.findAll(Specification.where(noteSpecification.byUser(user)),
                        Sort.by(new Sort.Order(noteSpecification.getDirection(isDesc), field))))
                .orElseGet(Collections::emptyList);
    }

    @Override
    public Optional<Note> getUserNoteById(Integer noteId) {
        return userService.getUser()
                .flatMap(user -> noteRepository.findOne(Specification
                        .where(noteSpecification.byUser(user))
                        .and(noteSpecification.byId(noteId))));
    }
}
