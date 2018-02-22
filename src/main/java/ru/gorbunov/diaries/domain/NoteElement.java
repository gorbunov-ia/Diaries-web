package ru.gorbunov.diaries.domain;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Note element entity.
 *
 * @author Gorbunov.ia
 */
@Entity
@Table(name = "t_NotesElements")
public class NoteElement extends GeneralEntity implements Swappable {

    /**
     * Note.
     */
    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "NoteID", nullable = false)
    private Note note = null;

    /**
     * Note element description.
     */
    @NotNull
    @Size(min = 1, max = 64)
    @Column(nullable = false, length = 64)
    private String description;

    /**
     * Sorting order.
     */
    private Integer sortBy = 0;

    /**
     * Date of Last Modified.
     */
    //@Basic(optional = false)
    @Column(name = "LastModified", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModified;

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

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

    @Override
    public void setSortBy(Integer sortBy) {
        this.sortBy = sortBy;
    }

    public Date getLastModified() {
        return lastModified;
    }

    @Override
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(getId());
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
        final NoteElement other = (NoteElement) obj;
        return Objects.equals(getId(), other.getId());
    }

}
