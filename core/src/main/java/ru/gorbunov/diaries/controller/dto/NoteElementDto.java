package ru.gorbunov.diaries.controller.dto;

import ru.gorbunov.diaries.controller.vm.SortElementVm;
import ru.gorbunov.diaries.domain.Movable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

/**
 * Note element Data Transfer Object.
 *
 * @author Gorbunov.ia
 */
public class NoteElementDto extends GeneralDto implements Movable {

    /**
     * Note element description.
     */
    @NotNull
    @Size(min = 1, max = 64)
    private String description;

    /**
     * Note identifier.
     */
    @NotNull
    @Min(1)
    private Integer noteId;

    /**
     * Sorting order.
     */
    private Integer sortBy;

    /**
     * Date of Last Modified.
     */
    private Date lastModified;

    /**
     * Class help to swap note elements on UI.
     */
    @Deprecated
    private SortElementVm sortElementVm;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    @Override
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
    public SortElementVm getSortElementVm() {
        return sortElementVm;
    }

    @Override
    public void setSortElementVm(SortElementVm sortElementVm) {
        this.sortElementVm = sortElementVm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NoteElementDto that = (NoteElementDto) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

}
