package ru.gorbunov.diaries.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.service.NoteService;

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
        final List<Note> notes = noteService.getUserNotesWithSort("lastModified", true);
        model.addAttribute("notes", notes);
        return "notes";
    }

}
