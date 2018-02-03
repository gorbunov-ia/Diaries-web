package ru.gorbunov.diaries.controller.vm;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * View Model to swap element http request.
 *
 * @author Gorbunov.ia
 */
public class SwapElementVM {

    /**
     * Editable note element id.
     */
    @NotNull
    private Integer noteElementId;

    /**
     * New sort by value.
     */
    @NotNull
    private Integer sortBy;

    /**
     * Default constructor.
     */
    public SwapElementVM() {
    }

    /**
     * Base constructor.
     *
     * @param noteElementId editable note element id
     * @param sortBy        new sort by for note element
     */
    public SwapElementVM(final Integer noteElementId, final Integer sortBy) {
        this.noteElementId = noteElementId;
        this.sortBy = sortBy;
    }

    public Integer getNoteElementId() {
        return noteElementId;
    }

    public Integer getSortBy() {
        return sortBy;
    }

    @Override
    public String toString() {
        return "SwapElementVM{"
                + "noteElementId=" + noteElementId
                + ", sortBy=" + sortBy + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.noteElementId);
        hash = 89 * hash + Objects.hashCode(this.sortBy);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SwapElementVM other = (SwapElementVM) obj;
        if (!Objects.equals(this.noteElementId, other.noteElementId)) {
            return false;
        }
        if (!Objects.equals(this.sortBy, other.sortBy)) {
            return false;
        }
        return true;
    }

}
