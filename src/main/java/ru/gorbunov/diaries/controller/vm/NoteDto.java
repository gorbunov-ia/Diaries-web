package ru.gorbunov.diaries.controller.vm;

import java.io.Serializable;
import java.util.Date;

/**
 * Note Data Transfer Object.
 *
 * @author Gorbunov.ia
 */
public class NoteDto implements Serializable {

    /**
     * Id entity.
     */
    private Integer id;

    /**
     * Note description.
     */
    private String description;

    /**
     * Sorting order.
     */
    private Integer sortBy;

    /**
     * Date of Last Modified.
     */
    private Date lastModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortBy() {
        return sortBy;
    }

    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}
