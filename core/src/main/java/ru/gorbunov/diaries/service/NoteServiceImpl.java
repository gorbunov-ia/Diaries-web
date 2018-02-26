package ru.gorbunov.diaries.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;

import java.util.Collections;
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
        final User user = userService.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return noteRepository.findAll(Specifications.where(noteSpecification.byUser(user)),
                new Sort(new Sort.Order(noteSpecification.getDirection(isDesc), field)));
    }

    @Override
    public Note getUserNoteById(Integer noteId) {
        final User user = userService.getUser();
        if (user == null) {
            return null;
        }
        return noteRepository.findOne(Specifications.where(noteSpecification.byUser(user))
                .and(noteSpecification.byId(noteId)));
    }
}
