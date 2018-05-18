package ru.gorbunov.diaries.controller.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NotNull
    @Size(min = 1, max = 64)
    private String description;

    /**
     * Sorting order.
     */
    @NotNull
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
