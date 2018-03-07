package ru.gorbunov.diaries.controller.vm;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 * Class help to swap note elements on UI.
 *
 * @author Gorbunov.ia
 */
public class SortElementVm {

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

    /**
     * Method to help calculate index shift for prev element in list.
     *
     * @param index     index in list for current element
     * @param listSize  list.size()
     * @return          index shift
     */
    public int getPrevIndexShift(int index, int listSize) {
        if (listSize == 1 || index == 0) {
            return 0;
        }
        return index - 1;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }

    /**
     * Method to help calculate index shift for next element in list.
     *
     * @param index     index in list for current element
     * @param listSize  list.size()
     * @return          index shift
     */
    public int getNextIndexShift(int index, int listSize) {
        if (listSize == 1 || index == listSize - 1) {
            return listSize - 1;
        }
        return index + 1;
    }

    public MutableInt getLast() {
        return last;
    }

    public void setLast(MutableInt last) {
        this.last = last;
    }

}
