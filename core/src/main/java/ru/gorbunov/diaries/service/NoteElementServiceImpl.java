package ru.gorbunov.diaries.service;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ru.gorbunov.diaries.controller.dto.NoteElementDto;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.service.internal.NoteElementInternalService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of service for interaction with note elements.
 *
 * @author Gorbunov.ia
 */
@Service
public class NoteElementServiceImpl implements NoteElementService {

    /**
     * Service for interaction with note elements.
     */
    private final NoteElementInternalService noteElementInternalService;

    /**
     * A service interface for type conversion.
     */
    private final ConversionService conversionService;

    /**
     * Base constructor.
     *
     * @param noteElementInternalService service for interaction with note elements.
     * @param conversionService          spring conversion service
     */
    NoteElementServiceImpl(NoteElementInternalService noteElementInternalService, ConversionService conversionService) {
        this.noteElementInternalService = noteElementInternalService;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Collection<NoteElementDto> changeSortBy(Integer noteElementId, Integer sortBy) {
        final Collection<NoteElement> noteElements = noteElementInternalService.changeSortBy(noteElementId, sortBy);
        return noteElements.stream()
                .map(noteElement -> conversionService.convert(noteElement, NoteElementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NoteElementDto> getUserNoteElementsByNoteWithSort(Integer noteId, String field, boolean isDesc) {
        final List<NoteElement> noteElements = noteElementInternalService
                .getUserNoteElementsByNoteWithSort(noteId, field, isDesc);
        return noteElements.stream()
                .map(noteElement -> conversionService.convert(noteElement, NoteElementDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NoteElementDto createNoteElement(NoteElementDto noteElementDto) {
        final NoteElement noteElement = noteElementInternalService.createNoteElement(noteElementDto);
        return conversionService.convert(noteElement, NoteElementDto.class);
    }

    @Override
    @Transactional
    public void deleteNoteElement(Integer noteElementId) {
        noteElementInternalService.deleteNoteElement(noteElementId);
    }

    @Override
    @Transactional
    public NoteElementDto updateNoteElement(NoteElementDto noteElementDto) {
        final NoteElement noteElement = noteElementInternalService.updateNoteElement(noteElementDto);
        return conversionService.convert(noteElement, NoteElementDto.class);
    }
}
