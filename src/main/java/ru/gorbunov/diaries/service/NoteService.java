package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.domain.Note;

import java.util.Collection;

/**
 * Service for interaction with notes.
 *
 * @author Gorbunov.ia
 */
public interface NoteService {

    /**
     * Method to get notes for current user with sorting
     *
     * @param field     sorting field
     * @param isDesc    ascending or descending sort
     * @return          collection note entity
     */
    Collection<Note> getUserNotesWithSort(String field, boolean isDesc);
}
