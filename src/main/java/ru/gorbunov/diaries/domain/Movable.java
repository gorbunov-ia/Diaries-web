package ru.gorbunov.diaries.domain;

import ru.gorbunov.diaries.controller.vm.SortElementVm;

/**
 * Interface for movable elements on UI.
 *
 * @author Gorbunov.ia
 */
public interface Movable {

    /**
     * Method to get field value for sorting.
     *
     * @return sort by
     */
    Integer getSortBy();

    /**
     * Method to get help class to swap elements on UI.
     *
     * @return sort element helper
     */
    SortElementVm getSortElementVm();

    /**
     * Method to set help class to swap elements on UI.
     *
     * @param sortElement sort element helper
     */
    void setSortElementVm(SortElementVm sortElement);

}
