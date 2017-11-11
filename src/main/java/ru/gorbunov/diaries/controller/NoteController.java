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
 *
 * @author Gorbunov.ia
 */
@Controller
@RequestMapping(path = "/notes")
public class NoteController {
    
    private final Logger log = LoggerFactory.getLogger(NoteController.class);    
    
    private final NoteRepository noteRepository;
    
    private final NoteSpecification noteSpecification;
    
    public NoteController (NoteRepository noteRepository, NoteSpecification noteSpecification) {
        this.noteRepository = noteRepository;
        this.noteSpecification = noteSpecification;
    }

    @ModelAttribute("module")
    public String module() {
        return "notes";
    }
    
    @GetMapping
    public String getAllNotes(ModelMap model) {
        log.debug("REST request to get Notes.");
        final List<Note> notes = noteRepository.findAll(Specifications
                .where(noteSpecification.byUser())
                .and(noteSpecification.orderBy("lastModified",true)));
        model.addAttribute("notes", notes);
        return "notes";
    }
    
}
