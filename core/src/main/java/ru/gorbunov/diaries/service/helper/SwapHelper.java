package ru.gorbunov.diaries.service.helper;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gorbunov.diaries.domain.Swappable;
import ru.gorbunov.diaries.exception.SwapElementException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Helper for swap elements.
 *
 * @param <T> swap element type
 * @author Gorbunov.ia
 */
public class SwapHelper<T extends Swappable> {

    /**
     * Shifted elements.
     */
    private final List<T> swapElementsForShift;
    /**
     * Element for swap.
     */
    private final T swapElement;
    /**
     * New sort by swap element value.
     */
    private final Integer newSortBy;
    /**
     * Old sort by swap element value.
     */
    private final Integer oldSortBy;

    /**
     * Base constructor.
     *
     * @param swapElementsForShift  shifted swap elements
     * @param swapElement           element for swap
     * @param newSortBy             new sort by element value
     */
    public SwapHelper(final List<T> swapElementsForShift, final T swapElement,
                      final Integer newSortBy) {
        this.swapElementsForShift = swapElementsForShift;
        this.swapElement = swapElement;
        this.newSortBy = newSortBy;
        this.oldSortBy = swapElement.getSortBy();
    }

    /**
     * Do swap elements and change sort by.
     *
     * @param swapElementRepository repository for save swap elements
     * @return                      swap elements with new sort by
     * @throws SwapElementException if swap helper construct with null parameters
     */
    public List<T> swap(final JpaRepository<T, ? extends Serializable> swapElementRepository) {
        Objects.requireNonNull(swapElementRepository);
        if (!isValid()) {
            throw new SwapElementException();
        }
        prepareSwapElementsForShift();
        saveSwapElementsForShift(swapElementRepository);
        swapElements();
        saveSwapElementsForShift(swapElementRepository);
        return getChangedElements();
    }

    /**
     * Valid swap helper parameters.
     *
     * @return true if parameters not contain nulls.
     */
    private boolean isValid() {
        if (swapElementsForShift == null || swapElement == null || newSortBy == null || oldSortBy == null) {
            return false;
        }
        return swapElementsContainSortBy();
    }

    /**
     * Search element with sort by in list.
     *
     * @return true if contains
     */
    private boolean swapElementsContainSortBy() {
        for (T element : swapElementsForShift) {
            if (element.getSortBy().equals(newSortBy)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prepare list of swap elements to shift.
     */
    private void prepareSwapElementsForShift() {
        //Stupid MySQL constraint
        for (T element : swapElementsForShift) {
            element.setSortBy(element.getSortBy() * (-1));
        }
        swapElement.setSortBy(swapElement.getSortBy() * (-1));
    }

    /**
     * Change sort by values for target element and shifted elements.
     */
    private void swapElements() {
        //Swap elements
        for (int i = 0; i < swapElementsForShift.size() - 1; i++) {
            Integer itemSortBy = swapElementsForShift.get(i).getSortBy();
            Integer nextItemSortBy = swapElementsForShift.get(i + 1).getSortBy();
            swapElementsForShift.get(i).setSortBy(nextItemSortBy * (-1));
            swapElementsForShift.get(i + 1).setSortBy(itemSortBy);
        }
        //Last swap
        swapElementsForShift.get(swapElementsForShift.size() - 1).setSortBy(oldSortBy);
        swapElement.setSortBy(newSortBy);
        swapElement.setLastModified(new Date());
    }

    /**
     * Save shift objects to db.
     *
     * @param swapElementRepository repository for save note elements in db.
     */
    private void saveSwapElementsForShift(final JpaRepository<T, ? extends Serializable> swapElementRepository) {
        swapElementRepository.save(swapElement);
        swapElementRepository.saveAll(swapElementsForShift);
        swapElementRepository.flush();
    }

    /**
     * Combine swap and shifted elements.
     *
     * @return elements with new sort by
     */
    private List<T> getChangedElements() {
        List<T> result = new ArrayList<>(swapElementsForShift.size() + 1);
        result.add(swapElement);
        result.addAll(swapElementsForShift);
        return result;
    }

}
