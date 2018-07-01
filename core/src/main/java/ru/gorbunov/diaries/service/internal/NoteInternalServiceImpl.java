package ru.gorbunov.diaries.service.internal;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.gorbunov.diaries.controller.dto.NoteDto;
import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.exception.BadRequestException;
import ru.gorbunov.diaries.exception.ResourceNotFoundException;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of service for interaction with notes.
 *
 * @author Gorbunov.ia
 */
@Service
public class NoteInternalServiceImpl implements NoteInternalService {

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
    private final UserInternalService userInternalService;

    /**
     * Base constructor.
     *
     * @param noteRepository    repository for crud operation with db
     * @param noteSpecification specification for add condition into query to db
     * @param userInternalService       service for interaction with user
     */
    public NoteInternalServiceImpl(final NoteRepository noteRepository, final NoteSpecification noteSpecification,
                                   final UserInternalService userInternalService) {
        this.noteRepository = noteRepository;
        this.noteSpecification = noteSpecification;
        this.userInternalService = userInternalService;
    }

    @Override
    public List<Note> getUserNotesWithSort(String field, boolean isDesc) {
        return userInternalService.getUser()
                .map(user -> noteRepository.findAll(Specification.where(noteSpecification.byUser(user)),
                        Sort.by(new Sort.Order(noteSpecification.getDirection(isDesc), field))))
                .orElseGet(Collections::emptyList);
    }

    @Override
    public Optional<Note> getUserNoteById(Integer noteId) {
        return userInternalService.getUser()
                .flatMap(user -> noteRepository.findOne(Specification
                        .where(noteSpecification.byUser(user))
                        .and(noteSpecification.byId(noteId))));
    }

    @Override
    @Transactional
    public Note createNote(NoteDto noteDto) {
        final Optional<User> user = userInternalService.getUser();
        return noteRepository.save(getNoteFromDto(noteDto,
                user.orElseThrow(BadRequestException::ofUser)));
    }

    @Override
    @Transactional
    public void deleteNote(Integer noteId) {
        final Optional<Note> note = noteRepository.findOne(noteSpecification
                .byUser(userInternalService.getUser().orElseThrow(BadRequestException::ofUser))
                .and(noteSpecification.byId(noteId)));
        noteRepository.delete(note.orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    @Transactional
    public Note updateNote(NoteDto noteDto) {
        final Optional<Note> note = noteRepository.findOne(noteSpecification
                .byUser(userInternalService.getUser().orElseThrow(BadRequestException::ofUser))
                .and(noteSpecification.byId(noteDto.getId())));
        return noteRepository.save(updateNoteFromDto(note.orElseThrow(ResourceNotFoundException::new), noteDto));
    }

    /**
     * Create note entity and fill field from dto.
     *
     * @param noteDto dto
     * @param user    user for set into entity
     * @return not saved entity
     */
    private Note getNoteFromDto(NoteDto noteDto, User user) {
        final Note note = new Note();
        note.setUser(user);
        note.setDescription(noteDto.getDescription());
        note.setSortBy(noteDto.getSortBy());
        note.setLastModified(new Date());
        return note;
    }

    /**
     * Update note field from dto.
     *
     * @param note note for update
     * @param dto  note dto with new data
     * @return note with new field values
     */
    private Note updateNoteFromDto(Note note, NoteDto dto) {
        note.setDescription(dto.getDescription());
        note.setSortBy(dto.getSortBy());
        note.setLastModified(new Date());
        return note;
    }
}
