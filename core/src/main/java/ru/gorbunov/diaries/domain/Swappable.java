package ru.gorbunov.diaries.domain;

import java.util.Date;

/**
 * Interface for swappable elements.
 *
 * @author Gorbunov.ia
 */
public interface Swappable {

    /**
     * Method to get field value for sorting.
     *
     * @return sort by
     */
    Integer getSortBy();

    /**
     * Method to set field value for sorting.
     *
     * @param sortBy sort by
     */
    void setSortBy(Integer sortBy);

    /**
     * Method to set date of element last modified.
     *
     * @param lastModified modified date
     */
    void setLastModified(Date lastModified);

}
