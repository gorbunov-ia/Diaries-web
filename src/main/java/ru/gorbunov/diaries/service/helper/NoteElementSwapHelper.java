package ru.gorbunov.diaries.service.helper;

import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.exception.SwapElementException;
import ru.gorbunov.diaries.repository.NoteElementRepository;

import java.util.Date;
import java.util.List;

/**
 * Helper for swap note elements.
 *
 * @author Gorbunov.ia
 */
public class NoteElementSwapHelper {

    /**
     * Shifted note elements.
     */
    private final List<NoteElement> noteElementsForShift;
    /**
     * Note element for swap.
     */
    private final NoteElement noteElement;
    /**
     * New sort by note element value.
     */
    private final Integer newSortBy;
    /**
     * Old sort by note element value.
     */
    private final Integer oldSortBy;

    /**
     * Base constructor.
     *
     * @param noteElementsForShift  shifted note elements
     * @param noteElement           note element for swap
     * @param newSortBy             new sort by note element value
     */
    public NoteElementSwapHelper(final List<NoteElement> noteElementsForShift, final NoteElement noteElement,
                                 final Integer newSortBy) {
        this.noteElementsForShift = noteElementsForShift;
        this.noteElement = noteElement;
        this.newSortBy = newSortBy;
        this.oldSortBy = noteElement.getSortBy();
    }

    /**
     * Do swap note elements and change sort by.
     *
     * @param noteElementRepository repository for save note elements
     * @return                      note element with new sort by
     * @throws SwapElementException if swap helper construct with null parameters
     */
    public NoteElement swap(final NoteElementRepository noteElementRepository) throws SwapElementException {
        if (noteElementRepository == null) {
            throw new IllegalArgumentException();
        }
        if (!isValid()) {
            throw new SwapElementException();
        }
        prepareNoteElementsForShift();
        saveNoteElementsForShift(noteElementRepository);
        swapNoteElements();
        saveNoteElementsForShift(noteElementRepository);
        return noteElement;
    }

    /**
     * Valid swap helper parameters.
     * @return true if parameters not contain nulls.
     */
    private boolean isValid() {
        if (noteElementsForShift == null || noteElement == null || newSortBy == null || oldSortBy == null) {
            return false;
        }
        return noteElementsContainSortBy();
    }

    /**
     * Search note element with sort by in list.
     *
     * @return true if contains
     */
    private boolean noteElementsContainSortBy() {
        for (NoteElement element : noteElementsForShift) {
            if (element.getSortBy().equals(newSortBy)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prepare list of note elements to shift.
     */
    private void prepareNoteElementsForShift() {
        //Stupid MySQL constraint
        for (NoteElement element : noteElementsForShift) {
            element.setSortBy(element.getSortBy() * (-1));
        }
        noteElement.setSortBy(noteElement.getSortBy() * (-1));
    }

    /**
     * Change sort by values for target note element and shifted note elements.
     */
    private void swapNoteElements() {
        //Swap elements
        for (int i = 0; i < noteElementsForShift.size() - 1; i++) {
            Integer itemSortBy = noteElementsForShift.get(i).getSortBy();
            Integer nextItemSortBy = noteElementsForShift.get(i + 1).getSortBy();
            noteElementsForShift.get(i).setSortBy(nextItemSortBy * (-1));
            noteElementsForShift.get(i + 1).setSortBy(itemSortBy);
        }
        //Last swap
        noteElementsForShift.get(noteElementsForShift.size() - 1).setSortBy(oldSortBy);
        noteElement.setSortBy(newSortBy);
        noteElement.setLastModified(new Date());
    }

    /**
     * Save shift objects to db.
     *
     * @param noteElementRepository repository for save note elements in db.
     */
    private void saveNoteElementsForShift(final NoteElementRepository noteElementRepository) {
        noteElementRepository.save(noteElement);
        noteElementRepository.save(noteElementsForShift);
        noteElementRepository.flush();
    }

}
