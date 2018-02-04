package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.domain.NoteElement;

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
     * @return              note element with new sort by
     */
    NoteElement changeSortBy(Integer noteElementId, Integer sortBy);

    /**
     * Add sortElementVm (helper on UI) to each note element.
     *
     * @param notesElements list of note element without sortElementVm
     */
    void fillSortElement(List<NoteElement> notesElements);

    /**
     * Method to get note elements for current user and note id with sorting.
     *
     * @param noteId    note id in db
     * @param field     sorting field
     * @param isDesc    ascending or descending sort
     * @return          collection note elements
     */
    List<NoteElement> getUserNoteElementsByNoteWithSort(Integer noteId, String field, boolean isDesc);
}
