package ru.gorbunov.diaries.service;

import ru.gorbunov.diaries.domain.NoteElement;

import java.util.List;

public interface NoteElementService {

    public NoteElement changeSortBy(Integer noteElementId, Integer sortBy);

    public void fillSortElement(List<NoteElement> notesElements);
}
