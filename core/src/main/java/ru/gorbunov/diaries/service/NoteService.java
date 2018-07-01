package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.controller.dto.NoteDto;

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
     * @param field  sorting field
     * @param isDesc ascending or descending sort
     * @return collection of note dto
     */
    List<NoteDto> getUserNotesWithSort(String field, boolean isDesc);

    /**
     * Method to get note for current user by note id.
     *
     * @param noteId note id in db
     * @return note dto if note id exist for current user
     */
    Optional<NoteDto> getUserNoteById(Integer noteId);

    /**
     * Method to create note for current user.
     *
     * @param noteDto dto
     * @return dto of created entity
     */
    NoteDto createNote(NoteDto noteDto);

    /**
     * Method to delete note by id.
     *
     * @param noteId identifier of entity
     */
    void deleteNote(Integer noteId);

    /**
     * Method to update note.
     *
     * @param noteDto dto
     * @return dto of updated entity
     */
    NoteDto updateNote(NoteDto noteDto);

}
