package ru.gorbunov.diaries.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;

/**
 *
 * @author Gorbunov.ia
 */
@Controller
@RequestMapping(path = "/notes-elements")
public class NoteElementController {
    
    private final Logger log = LoggerFactory.getLogger(NoteController.class);    
    
    private final NoteElementRepository noteElementRepository;
    
    private final NoteElementSpecification noteElementSpecification;
    
    private final NoteRepository noteRepository;
    
    private final NoteSpecification noteSpecification;
    
    public NoteElementController (NoteElementRepository noteElementRepository, 
            NoteElementSpecification noteElementSpecification,
            NoteRepository noteRepository,
            NoteSpecification noteSpecification) {        
        this.noteElementRepository = noteElementRepository;
        this.noteElementSpecification = noteElementSpecification;
        this.noteRepository = noteRepository;
        this.noteSpecification = noteSpecification;
    }
    
    @GetMapping("/{noteId}")
    public String getAllNotes(@PathVariable Integer noteId,ModelMap model) {
        log.debug("REST request to get NotesElements.");
        
        final Note note = noteRepository.findOne(Specifications
                .where(noteSpecification.byUser())
                .and(noteSpecification.byId(noteId)));
        
        //TODO: not found page
        
        final List<NoteElement> notesElements = noteElementRepository.findAll(
                Specifications
                .where(noteElementSpecification.byUser())
                .and(noteElementSpecification.byNote(note.getId())));
        
        model.addAttribute("note", note);
        model.addAttribute("notesElements", notesElements);
        
        return "notes-elements";
    }    
}
