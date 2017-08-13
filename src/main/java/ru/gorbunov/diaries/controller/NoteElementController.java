package ru.gorbunov.diaries.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;

/**
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "/notes-elements")
public class NoteElementController {
    
    private final Logger log = LoggerFactory.getLogger(NoteController.class);    
    
    private final NoteElementRepository noteElementRepository;
    
    private final NoteElementSpecification noteElementSpecification;
    
    public NoteElementController (NoteElementRepository repository, NoteElementSpecification noteElementSpecification) {
        this.noteElementRepository = repository;
        this.noteElementSpecification = noteElementSpecification;
    }
    
    @GetMapping
    public ResponseEntity<List<NoteElement>> getAllNotes() {
        log.debug("REST request to get NotesElements.");
        final List<NoteElement> notesElements = noteElementRepository.findAll(noteElementSpecification.byUser());
        return new ResponseEntity<>(notesElements, HttpStatus.OK);
    }    
}
