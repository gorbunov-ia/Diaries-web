package ru.gorbunov.diaries.controller;

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

import ru.gorbunov.diaries.controller.dto.NoteElementDto;
import ru.gorbunov.diaries.controller.vm.SwapElementVm;
import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.exception.BadRequestException;
import ru.gorbunov.diaries.exception.ResourceNotFoundException;
import ru.gorbunov.diaries.service.NoteElementService;
import ru.gorbunov.diaries.service.NoteInternalService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final Logger log = LoggerFactory.getLogger(NoteController.class);

    /**
     * Service for interaction with notes.
     */
    private final NoteInternalService noteInternalService;

    /**
     * Service for interaction with note elements.
     */
    private final NoteElementService noteElementService;

    /**
     * A service interface for type conversion.
     */
    private final ConversionService conversionService;

    /**
     * Base constructor.
     *
     * @param noteInternalService        service for interaction with notes.
     * @param noteElementService service for interaction with note elements.
     * @param conversionService  Spring conversion service
     */
    public NoteElementController(final NoteInternalService noteInternalService,
                                 final NoteElementService noteElementService,
                                 final ConversionService conversionService) {
        this.noteInternalService = noteInternalService;
        this.noteElementService = noteElementService;
        this.conversionService = conversionService;
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
        log.debug("REST request to get NotesElements.");

        final Optional<Note> note = noteInternalService.getUserNoteById(noteId);
        if (!note.isPresent()) {
            throw new ResourceNotFoundException();
        }
        final List<NoteElement> notesElements = noteElementService.getUserNoteElementsByNoteWithSort(note.get().getId(),
                "sortBy", true);
        if (notesElements.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        final List<NoteElementDto> notesElementsDto = notesElements.stream()
                .map(noteElement -> conversionService.convert(noteElement, NoteElementDto.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(notesElementsDto);
    }

    /**
     * Method set new sort by value to note element.
     *
     * @param swapElementVm data required for swap
     * @return list of note element dto
     */
    @PostMapping(value = "/swap")
    public ResponseEntity<Collection<NoteElementDto>> swap(@Valid @RequestBody SwapElementVm swapElementVm) {
        log.debug("REST request to swap Note Element.");
        Collection<NoteElement> elements = noteElementService.changeSortBy(swapElementVm.getNoteElementId(),
                                                                           swapElementVm.getSortBy());
        return ResponseEntity.ok(elements.stream()
                    .map(element -> conversionService.convert(element, NoteElementDto.class))
                    .collect(Collectors.toList()));
    }

    /**
     * Method to create new note element.
     *
     * @param noteElementDto data for create note element
     * @return dto with created note element
     */
    @PostMapping
    public ResponseEntity<NoteElementDto> createNoteElement(@Valid @RequestBody NoteElementDto noteElementDto) {
        log.debug("REST request to create note element: {}", noteElementDto);
        if (Objects.nonNull(noteElementDto.getId())) {
            throw BadRequestException.ofPresentId();
        }
        final NoteElement noteElement = noteElementService.createNoteElement(noteElementDto);
        return ResponseEntity.ok(conversionService.convert(noteElement, NoteElementDto.class));
    }

    /**
     * Method to delete note element from db.
     *
     * @param noteElementId note element identification
     * @return http status
     */
    @DeleteMapping("/{noteElementId}")
    public ResponseEntity<Void> deleteNoteElement(@PathVariable final Integer noteElementId) {
        log.debug("REST request to delete note element: {}", noteElementId);
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
        log.debug("REST request to update note element: {}", noteElementDto);
        if (Objects.isNull(noteElementDto.getId())) {
            throw BadRequestException.ofAbsentId();
        }
        final NoteElement noteElement = noteElementService.updateNoteElement(noteElementDto);
        return ResponseEntity.ok(conversionService.convert(noteElement, NoteElementDto.class));
    }

}
