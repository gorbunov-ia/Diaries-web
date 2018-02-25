package ru.gorbunov.diaries.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.gorbunov.diaries.controller.dto.NoteDto;
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
     * A service interface for type conversion.
     */
    private final ConversionService conversionService;

    /**
     * Base constructor.
     *
     * @param noteService service for interaction with notes.
     * @param conversionService Spring conversion service
     */
    public NoteController(final NoteService noteService, ConversionService conversionService) {
        this.noteService = noteService;
        this.conversionService = conversionService;
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
        List<NoteDto> notesDto = notes.stream().map(note -> conversionService.convert(note, NoteDto.class))
                .collect(Collectors.toList());
        model.addAttribute("notes", notesDto);
        return "notes";
    }

}
