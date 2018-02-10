package ru.gorbunov.diaries.controller.vm;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 * Class help to swap note elements on UI.
 *
 * @author Gorbunov.ia
 */
public class SortElementVM {

    /**
     * Sort by field first note element on page.
     */
    private MutableInt first;

    /**
     * Sort by field previous note element on page.
     */
    private Integer prev;

    /**
     * Sort by field next note element on page.
     */
    private Integer next;

    /**
     * Sort by field last note element on page.
     */
    private MutableInt last;

    public MutableInt getFirst() {
        return first;
    }

    public void setFirst(MutableInt first) {
        this.first = first;
    }

    public Integer getPrev() {
        return prev;
    }

    public void setPrev(Integer prev) {
        this.prev = prev;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    public MutableInt getLast() {
        return last;
    }

    public void setLast(MutableInt last) {
        this.last = last;
    }

}
