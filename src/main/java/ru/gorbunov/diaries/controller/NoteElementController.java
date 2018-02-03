package ru.gorbunov.diaries.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.exception.BadRequestException;
import ru.gorbunov.diaries.exception.ResourceNotFoundException;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;
import ru.gorbunov.diaries.service.NoteElementService;

/**
 * Controller for note elements page.
 *
 * @author Gorbunov.ia
 */
@Controller
@RequestMapping(path = "/notes-elements")
public class NoteElementController {

    /**
     * Logger for class.
     */
    private final Logger log = LoggerFactory.getLogger(NoteController.class);

    /**
     * Repository for Note Elements.
     */
    private final NoteElementRepository noteElementRepository;

    /**
     * Specification for Note Elements.
     */
    private final NoteElementSpecification noteElementSpecification;

    /**
     * Repository for Note.
     */
    private final NoteRepository noteRepository;

    /**
     * Specification for Note.
     */
    private final NoteSpecification noteSpecification;

    /**
     * Service for interaction with note elements.
     */
    private NoteElementService noteElementService;

    /**
     * Base constructor.
     *
     * @param noteElementRepository     note element repository for crud operation with db
     * @param noteElementSpecification  note element specification for add condition into query to db
     * @param noteRepository            note repository for crud operation with db
     * @param noteSpecification         note specification for add condition into query to db
     */
    public NoteElementController(final NoteElementRepository noteElementRepository,
                                 final NoteElementSpecification noteElementSpecification,
                                 final NoteRepository noteRepository,
                                 final NoteSpecification noteSpecification) {
        this.noteElementRepository = noteElementRepository;
        this.noteElementSpecification = noteElementSpecification;
        this.noteRepository = noteRepository;
        this.noteSpecification = noteSpecification;
    }

    public NoteElementService getNoteElementService() {
        return noteElementService;
    }

    @Autowired
    public void setNoteElementService(NoteElementService noteElementService) {
        this.noteElementService = noteElementService;
    }

    /**
     * Method to get all note elements for note.
     *
     * @param noteId    note id
     * @param model     Model Spring MVC
     * @return          template name
     * @throws          ResourceNotFoundException if note not found in db
     */
    @GetMapping("/{noteId}")
    public String getAllNoteElements(@PathVariable final Integer noteId, ModelMap model) {
        log.debug("REST request to get NotesElements.");

        final Note note = noteRepository.findOne(
                Specifications
                        .where(noteSpecification.byUser())
                        .and(noteSpecification.byId(noteId)));

        if (note == null) {
            throw new ResourceNotFoundException();
        }

        List<NoteElement> notesElements = noteElementRepository.findAll(
                Specifications
                        .where(noteElementSpecification.byUser())
                        .and(noteElementSpecification.byNote(note.getId()))
                        .and(noteElementSpecification.orderBy("sortBy", true)));

        noteElementService.fillSortElement(notesElements);

        model.addAttribute("note", note);
        model.addAttribute("notesElements", notesElements);

        return "notes-elements";
    }

/*
    @PostMapping(value="/swapJson", headers="Content type")
    public String swap(@Valid @RequestBody SwapElementVM swapVM) {

        NoteElement element = noteElementService.changeSortBy(swapVM
                .getNoteElementId(),swapVM.getSortBy());

        if (element != null) {
            return "redirect:/notes-elements/" + element.getNote().getId();
        } else {
            throw new BadRequestException();
        }
    }
*/

    /**
     * Method set new sort by value to note element.
     *
     * @param noteElementId modifiable note element id
     * @param sortBy        new sort by value
     * @return              template name
     * @throws              BadRequestException if note element id or sort by is wrong
     */
    @PostMapping(value = "/swap")
    public String swap(@RequestParam("noteElementId") final Integer noteElementId,
                       @RequestParam("sortBy") final Integer sortBy) {

        NoteElement element = noteElementService.changeSortBy(noteElementId, sortBy);

        if (element != null) {
            return "redirect:/notes-elements/" + element.getNote().getId();
        } else {
            throw new BadRequestException();
        }
    }

}
