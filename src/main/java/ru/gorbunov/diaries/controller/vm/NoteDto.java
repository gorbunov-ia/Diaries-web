package ru.gorbunov.diaries.controller.vm;

import java.util.Date;
import java.util.Objects;

/**
 * Note Data Transfer Object.
 *
 * @author Gorbunov.ia
 */
public class NoteDto extends GeneralDto {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NoteDto noteDto = (NoteDto) o;
        return Objects.equals(getId(), noteDto.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }
}
