package ru.gorbunov.diaries.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.gorbunov.diaries.controller.dto.NoteDto;
import ru.gorbunov.diaries.exception.BadRequestException;
import ru.gorbunov.diaries.service.NoteService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Controller for notes page.
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "api/notes")
public class NoteController {

    /**
     * Logger for class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(NoteController.class);

    /**
     * Service for interaction with notes.
     */
    private final NoteService noteService;

    /**
     * Base constructor.
     *
     * @param noteService service for interaction with notes.
     */
    public NoteController(final NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * Method to get all notes for user.
     *
     * @return list of notes dto
     */
    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        LOG.debug("REST request to get Notes.");
        final List<NoteDto> notesDto = noteService.getUserNotesWithSort("sortBy", true);
        return ResponseEntity.ok(notesDto);
    }

    /**
     * Method to get notes by id.
     *
     * @param noteId note identification
     * @return note dto
     */
    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable final Integer noteId) {
        LOG.debug("REST request to get Note: {}", noteId);
        final Optional<NoteDto> noteDto = noteService.getUserNoteById(noteId);
        return noteDto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Method to create new note.
     *
     * @param noteDto data for create note
     * @return dto with created note
     */
    @PostMapping
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto noteDto) {
        LOG.debug("REST request to create note: {}", noteDto);
        if (noteDto.getId() != null) {
            throw BadRequestException.ofPresentId();
        }
        final NoteDto note = noteService.createNote(noteDto);
        return ResponseEntity.ok(note);
    }

    /**
     * Method to delete note from db.
     *
     * @param noteId note identification
     * @return http status
     */
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable final Integer noteId) {
        LOG.debug("REST request to delete note: {}", noteId);
        noteService.deleteNote(noteId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Method to update note.
     *
     * @param noteDto dto
     * @return dto with new field values
     */
    @PutMapping
    public ResponseEntity<NoteDto> updateNote(@Valid @RequestBody NoteDto noteDto) {
        LOG.debug("REST request to update note: {}", noteDto);
        if (noteDto.getId() == null) {
            throw BadRequestException.ofAbsentId();
        }
        final NoteDto note = noteService.updateNote(noteDto);
        return ResponseEntity.ok(note);
    }

}
