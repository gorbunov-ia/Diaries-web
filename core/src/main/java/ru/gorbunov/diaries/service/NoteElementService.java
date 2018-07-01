package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.controller.dto.NoteElementDto;

import java.util.Collection;
import java.util.List;

/**
 * Service for interaction with note elements.
 *
 * @author Gorbunov.ia
 */
public interface NoteElementService {

    /**
     * Method set new sort by for note element with shift.
     *
     * @param noteElementId editing note element
     * @param sortBy        new sort by
     * @return collection of note element dto with new sort by
     * @throws NullPointerException                               if arguments contains null
     * @throws ru.gorbunov.diaries.exception.SwapElementException if swap failure
     */
    Collection<NoteElementDto> changeSortBy(Integer noteElementId, Integer sortBy);

    /**
     * Method to get note elements for current user and note id with sorting.
     *
     * @param noteId note id in db
     * @param field  sorting field
     * @param isDesc ascending or descending sort
     * @return collection note elements
     */
    List<NoteElementDto> getUserNoteElementsByNoteWithSort(Integer noteId, String field, boolean isDesc);

    /**
     * Method to create note element for current user.
     *
     * @param noteElementDto dto
     * @return dto of created entity
     */
    NoteElementDto createNoteElement(NoteElementDto noteElementDto);

    /**
     * Method to delete note element by id.
     *
     * @param noteElementId identifier of entity
     */
    void deleteNoteElement(Integer noteElementId);

    /**
     * Method to update note element.
     *
     * @param noteElementDto dto
     * @return dto of updated entity
     */
    NoteElementDto updateNoteElement(NoteElementDto noteElementDto);


}
