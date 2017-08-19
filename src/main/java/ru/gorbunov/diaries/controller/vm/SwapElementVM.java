package ru.gorbunov.diaries.controller.vm;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Gorbunov.ia
 */
public class SwapElementVM {
    
    @NotNull
    private Integer noteElementId;
    
    @NotNull
    private Integer sortBy;

    public SwapElementVM() {
    }
    
    public SwapElementVM(Integer noteElementId, Integer sortBy) {
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
