package ru.gorbunov.diaries.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;

/**
 * Controller for notes page.
 *
 * @author Gorbunov.ia
 */
@Controller
@RequestMapping(path = "/notes")
public class NoteController {

    /**
     * Logger for class.
     */
    private final Logger log = LoggerFactory.getLogger(NoteController.class);

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
    public NoteController(final NoteRepository noteRepository,
                          final NoteSpecification noteSpecification) {
        this.noteRepository = noteRepository;
        this.noteSpecification = noteSpecification;
    }

    /**
     * Method to get current module for template.
     *
     * @return module name
     */
    @ModelAttribute("module")
    public String module() {
        return "notes";
    }

    /**
     * Method to get all notes for user.
     *
     * @param model Model Spring MVC
     * @return      template name
     */
    @GetMapping
    public String getAllNotes(ModelMap model) {
        log.debug("REST request to get Notes.");
        final List<Note> notes = noteRepository.findAll(Specifications
                .where(noteSpecification.byUser())
                .and(noteSpecification.orderBy("lastModified", true)));
        model.addAttribute("notes", notes);
        return "notes";
    }

}
