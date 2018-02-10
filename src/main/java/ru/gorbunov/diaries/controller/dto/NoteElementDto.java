package ru.gorbunov.diaries.controller.dto;

import ru.gorbunov.diaries.controller.vm.SortElementVm;
import ru.gorbunov.diaries.domain.Movable;

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
    private String description;

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
    private SortElementVm sortElementVm;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
