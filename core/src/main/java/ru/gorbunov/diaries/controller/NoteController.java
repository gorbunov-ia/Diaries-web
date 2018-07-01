package ru.gorbunov.diaries.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.convert.ConversionService;
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
import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.exception.BadRequestException;
import ru.gorbunov.diaries.service.internal.NoteInternalService;

import javax.validation.Valid;

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
    private final Logger log = LoggerFactory.getLogger(NoteController.class);

    /**
     * Service for interaction with notes.
     */
    private final NoteInternalService noteInternalService;

    /**
     * A service interface for type conversion.
     */
    private final ConversionService conversionService;

    /**
     * Base constructor.
     *
     * @param noteInternalService service for interaction with notes.
     * @param conversionService Spring conversion service
     */
    public NoteController(final NoteInternalService noteInternalService, ConversionService conversionService) {
        this.noteInternalService = noteInternalService;
        this.conversionService = conversionService;
    }

    /**
     * Method to get all notes for user.
     *
     * @return list of notes dto
     */
    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes() {
        log.debug("REST request to get Notes.");
        final List<Note> notes = noteInternalService.getUserNotesWithSort("sortBy", true);
        List<NoteDto> notesDto = notes.stream().map(note -> conversionService.convert(note, NoteDto.class))
                .collect(Collectors.toList());
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
        log.debug("REST request to get Note: {}", noteId);
        final Optional<Note> note = noteInternalService.getUserNoteById(noteId);
        return note.map((element) -> ResponseEntity.ok(conversionService.convert(element, NoteDto.class)))
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
        log.debug("REST request to create note: {}", noteDto);
        if (Objects.nonNull(noteDto.getId())) {
            throw BadRequestException.ofPresentId();
        }
        final Note note = noteInternalService.createNote(noteDto);
        return ResponseEntity.ok(conversionService.convert(note, NoteDto.class));
    }

    /**
     * Method to delete note from db.
     *
     * @param noteId note identification
     * @return http status
     */
    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable final Integer noteId) {
        log.debug("REST request to delete note: {}", noteId);
        noteInternalService.deleteNote(noteId);
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
        log.debug("REST request to update note: {}", noteDto);
        if (Objects.isNull(noteDto.getId())) {
            throw BadRequestException.ofAbsentId();
        }
        final Note note = noteInternalService.updateNote(noteDto);
        return ResponseEntity.ok(conversionService.convert(note, NoteDto.class));
    }

}
