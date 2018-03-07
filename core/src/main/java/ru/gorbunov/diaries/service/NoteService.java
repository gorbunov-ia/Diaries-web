package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.domain.Note;

import java.util.List;
import java.util.Optional;

/**
 * Service for interaction with notes.
 *
 * @author Gorbunov.ia
 */
public interface NoteService {

    /**
     * Method to get notes for current user with sorting.
     *
     * @param field     sorting field
     * @param isDesc    ascending or descending sort
     * @return          collection note entity
     */
    List<Note> getUserNotesWithSort(String field, boolean isDesc);

    /**
     * Method to get note for current user by note id.
     *
     * @param noteId note id in db
     * @return note entity or null if note id does not exist for current user
     */
    Optional<Note> getUserNoteById(Integer noteId);
}
