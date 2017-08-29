package ru.gorbunov.diaries.controller.vm;

import org.apache.commons.lang3.mutable.MutableInt;

/**
 *
 * @author Gorbunov.ia
 */
public class SortElementVM {

    private MutableInt first;
    
    private Integer prev;        
    
    private Integer next;
        
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
