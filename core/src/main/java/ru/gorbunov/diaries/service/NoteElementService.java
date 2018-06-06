package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.controller.dto.NoteElementDto;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.domain.Movable;

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
     * @return note elements with new sort by
     * @throws NullPointerException                               if arguments contains null
     * @throws ru.gorbunov.diaries.exception.SwapElementException if swap failure
     */
    Collection<NoteElement> changeSortBy(Integer noteElementId, Integer sortBy);

    /**
     * Add sortElementVm (helper on UI) to each note element.
     *
     * @param movables list of movable elements without sortElementVm
     */
    @Deprecated
    void fillSortElement(List<? extends Movable> movables);

    /**
     * Method to get note elements for current user and note id with sorting.
     *
     * @param noteId note id in db
     * @param field  sorting field
     * @param isDesc ascending or descending sort
     * @return collection note elements
     */
    List<NoteElement> getUserNoteElementsByNoteWithSort(Integer noteId, String field, boolean isDesc);

    /**
     * Method to create note element for current user.
     *
     * @param noteElementDto dto
     * @return created entity
     */
    NoteElement createNoteElement(NoteElementDto noteElementDto);

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
     * @return updated entity
     */
    NoteElement updateNoteElement(NoteElementDto noteElementDto);

}
