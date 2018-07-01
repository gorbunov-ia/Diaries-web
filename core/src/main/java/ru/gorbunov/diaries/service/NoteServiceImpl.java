package ru.gorbunov.diaries.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.gorbunov.diaries.controller.dto.NoteDto;
import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.service.internal.NoteInternalService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of service for interaction with notes.
 *
 * @author Gorbunov.ia
 */
@Service
public class NoteServiceImpl implements NoteService {

    /**
     * Internal service for interaction with notes.
     */
    private final NoteInternalService noteInternalService;

    /**
     * A service interface for type conversion.
     */
    private final ConversionService conversionService;

    /**
     * Base constructor.
     *
     * @param noteInternalService internal service for interaction with notes.
     * @param conversionService   spring conversion service
     */
    NoteServiceImpl(NoteInternalService noteInternalService, ConversionService conversionService) {
        this.noteInternalService = noteInternalService;
        this.conversionService = conversionService;
    }

    @Override
    public List<NoteDto> getUserNotesWithSort(String field, boolean isDesc) {
        final List<Note> notes = noteInternalService.getUserNotesWithSort(field, isDesc);
        return notes.stream()
                .map(note -> conversionService.convert(note, NoteDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NoteDto> getUserNoteById(Integer noteId) {
        final Optional<Note> note = noteInternalService.getUserNoteById(noteId);
        return note.map((element) -> conversionService.convert(element, NoteDto.class));
    }

    @Override
    @Transactional
    public NoteDto createNote(NoteDto noteDto) {
        final Note note = noteInternalService.createNote(noteDto);
        return conversionService.convert(note, NoteDto.class);
    }

    @Override
    @Transactional
    public void deleteNote(Integer noteId) {
        noteInternalService.deleteNote(noteId);
    }

    @Override
    @Transactional
    public NoteDto updateNote(NoteDto noteDto) {
        final Note note = noteInternalService.updateNote(noteDto);
        return conversionService.convert(note, NoteDto.class);
    }

}
