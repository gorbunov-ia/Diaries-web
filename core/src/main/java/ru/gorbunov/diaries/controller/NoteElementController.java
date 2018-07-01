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
import ru.gorbunov.diaries.controller.dto.NoteElementDto;
import ru.gorbunov.diaries.controller.vm.SwapElementVm;
import ru.gorbunov.diaries.exception.BadRequestException;
import ru.gorbunov.diaries.exception.ResourceNotFoundException;
import ru.gorbunov.diaries.service.NoteElementService;
import ru.gorbunov.diaries.service.NoteService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Controller for note elements page.
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "api/notes-elements")
public class NoteElementController {

    /**
     * Logger for class.
     */
    private static final Logger LOG = LoggerFactory.getLogger(NoteController.class);

    /**
     * Service for interaction with notes.
     */
    private final NoteService noteService;

    /**
     * Service for interaction with note elements.
     */
    private final NoteElementService noteElementService;

    /**
     * Base constructor.
     *
     * @param noteService        service for interaction with notes.
     * @param noteElementService service for interaction with note elements.
     */
    public NoteElementController(final NoteService noteService, final NoteElementService noteElementService) {
        this.noteService = noteService;
        this.noteElementService = noteElementService;
    }

    /**
     * Method to get all note elements for note.
     *
     * @param noteId note id
     * @return list of note element dto
     * @throws ResourceNotFoundException if note not found in db
     */
    @GetMapping("/{noteId}")
    public ResponseEntity<List<NoteElementDto>> getAllNoteElements(@PathVariable final Integer noteId) {
        LOG.debug("REST request to get NotesElements.");
        final Optional<NoteDto> note = noteService.getUserNoteById(noteId);
        if (!note.isPresent()) {
            throw new ResourceNotFoundException();
        }
        final List<NoteElementDto> notesElements = noteElementService
                .getUserNoteElementsByNoteWithSort(note.get().getId(), "sortBy", true);
        return ResponseEntity.ok(notesElements);
    }

    /**
     * Method set new sort by value to note element.
     *
     * @param swapElementVm data required for swap
     * @return list of note element dto
     */
    @PostMapping(value = "/swap")
    public ResponseEntity<Collection<NoteElementDto>> swap(@Valid @RequestBody SwapElementVm swapElementVm) {
        LOG.debug("REST request to swap Note Element.");
        Collection<NoteElementDto> elements = noteElementService.changeSortBy(swapElementVm.getNoteElementId(),
                swapElementVm.getSortBy());
        return ResponseEntity.ok(elements);
    }

    /**
     * Method to create new note element.
     *
     * @param noteElementDto data for create note element
     * @return dto with created note element
     */
    @PostMapping
    public ResponseEntity<NoteElementDto> createNoteElement(@Valid @RequestBody NoteElementDto noteElementDto) {
        LOG.debug("REST request to create note element: {}", noteElementDto);
        if (noteElementDto.getId() != null) {
            throw BadRequestException.ofPresentId();
        }
        final NoteElementDto noteElement = noteElementService.createNoteElement(noteElementDto);
        return ResponseEntity.ok(noteElement);
    }

    /**
     * Method to delete note element from db.
     *
     * @param noteElementId note element identification
     * @return http status
     */
    @DeleteMapping("/{noteElementId}")
    public ResponseEntity<Void> deleteNoteElement(@PathVariable final Integer noteElementId) {
        LOG.debug("REST request to delete note element: {}", noteElementId);
        noteElementService.deleteNoteElement(noteElementId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    /**
     * Method to update note element.
     *
     * @param noteElementDto dto
     * @return dto with new field values
     */
    @PutMapping
    public ResponseEntity<NoteElementDto> updateNote(@Valid @RequestBody NoteElementDto noteElementDto) {
        LOG.debug("REST request to update note element: {}", noteElementDto);
        if (noteElementDto.getId() == null) {
            throw BadRequestException.ofAbsentId();
        }
        final NoteElementDto noteElement = noteElementService.updateNoteElement(noteElementDto);
        return ResponseEntity.ok(noteElement);
    }

}
