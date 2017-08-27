package ru.gorbunov.diaries.controller;

import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.controller.vm.SwapElementVM;
import ru.gorbunov.diaries.exception.BadRequestException;
import ru.gorbunov.diaries.exception.ResourceNotFoundException;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;
import ru.gorbunov.diaries.service.NoteElementService;

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
    
    private NoteElementService noteElementService;
    
    public NoteElementController (NoteElementRepository noteElementRepository, 
            NoteElementSpecification noteElementSpecification,
            NoteRepository noteRepository,
            NoteSpecification noteSpecification) {        
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
    
    @GetMapping("/{noteId}")
    public String getAllNotes(@PathVariable Integer noteId, ModelMap model) {
        log.debug("REST request to get NotesElements.");
        
        final Note note = noteRepository.findOne(
                Specifications
                        .where(noteSpecification.byUser())
                        .and(noteSpecification.byId(noteId)));
                
        if (note == null)
            throw new ResourceNotFoundException();
        
        final List<NoteElement> notesElements = noteElementRepository.findAll(
                Specifications
                        .where(noteElementSpecification.byUser())
                        .and(noteElementSpecification.byNote(note.getId())));
        
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

    @PostMapping(value="/swap")    
    public String swap(@RequestParam("noteElementId") 
            Integer noteElementId, @RequestParam("sortBy") Integer sortBy) {
        
        NoteElement element = noteElementService.changeSortBy(noteElementId,
                                                              sortBy);
        
        if (element != null) {
            return "redirect:/notes-elements/" + element.getNote().getId();
        } else {
            throw new BadRequestException();
        }   
    }
    
}
