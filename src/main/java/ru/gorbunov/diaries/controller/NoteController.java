package ru.gorbunov.diaries.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.repository.NoteRepository;

/**
 *
 * @author Gorbunov.ia
 */
@RestController
@RequestMapping(path = "/notes")
public class NoteController {
    
    private final Logger log = LoggerFactory.getLogger(NoteController.class);    
    
    private final NoteRepository noteRepository;
    
    public NoteController (NoteRepository repository) {
        this.noteRepository = repository;
    }
    
    @GetMapping
    public ResponseEntity<List<Note>> getAllNotes() {
        log.debug("REST request to get Notes for user: ","login");
        final List<Note> notes = (List<Note>) noteRepository.findAll();        
        return new ResponseEntity<>(notes, HttpStatus.OK);
    }
    
}
