package ru.gorbunov.diaries.service;

import org.apache.commons.lang3.mutable.MutableInt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gorbunov.diaries.controller.vm.SortElementVM;

import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.exception.SwapElementException;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.NoteRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;
import ru.gorbunov.diaries.repository.specification.NoteSpecification;

/**
 *
 * @author Gorbunov.ia
 */
@Service
public class NoteElementService {
    
    private final Logger log = LoggerFactory.getLogger(NoteElementService.class); 
    
    private final NoteElementRepository noteElementRepository;
    
    private final NoteElementSpecification noteElementSpecification;
    
    private final NoteRepository noteRepository;
    
    private final NoteSpecification noteSpecification;
    
    private NoteElementService noteElementService;
    
    public NoteElementService (NoteElementRepository noteElementRepository, 
            NoteElementSpecification noteElementSpecification,
            NoteRepository noteRepository,
            NoteSpecification noteSpecification) {        
        this.noteElementRepository = noteElementRepository;
        this.noteElementSpecification = noteElementSpecification;
        this.noteRepository = noteRepository;
        this.noteSpecification = noteSpecification;
    }    
    
    @Transactional(isolation = Isolation.REPEATABLE_READ , rollbackFor = Exception.class)
    public NoteElement changeSortBy(Integer noteElementId, Integer sortBy) {
        try {        
            NoteElement noteElement = noteElementRepository.findOne(
                    Specifications
                            .where(noteElementSpecification.byUser())
                            .and(noteElementSpecification.byId(noteElementId)));

            if (noteElement == null 
                    || noteElement.getSortBy().equals(sortBy))
                throw new SwapElementException();

            final Integer oldSortBy = noteElement.getSortBy();

            NoteElement noteElementWithNewSortBy = noteElementRepository.findOne(
                    Specifications
                            .where(noteElementSpecification.bySortBy(sortBy))
                            .and(noteElementSpecification
                                    .byNote(noteElement.getNote().getId())));

            if (noteElementWithNewSortBy == null)
                throw new SwapElementException();

            //Stupid MySQL constraint
            noteElement.setSortBy(sortBy * (-1));
            noteElementWithNewSortBy.setSortBy(oldSortBy);

            noteElementRepository.saveAndFlush(noteElement);            
            noteElementRepository.save(noteElementWithNewSortBy);

            noteElement.setSortBy(sortBy);
            noteElement.setLastModified(new Date());

            noteElementRepository.save(noteElement);
            return noteElement;
        } catch (Exception e) {
            log.warn("Swap error ID: {}, sortBy: {}", noteElementId, sortBy);
            return null;
        }
    }      

    public void fillSortElement(List<NoteElement> notesElements) {        
        MutableInt first = new MutableInt();
        MutableInt last = new MutableInt();
        int maxSortBy = -1;
        int minSortBy = -1;
        for (int i = 0; i < notesElements.size(); i++) {
            NoteElement element = notesElements.get(i);
            SortElementVM sort = new SortElementVM();
            element.setSortElementVm(sort);
            sort.setFirst(first);
            sort.setLast(last);
            if (notesElements.size() == 1) {
                sort.setPrev(element.getSortBy());
                sort.setNext(element.getSortBy());
                minSortBy = element.getSortBy();
                maxSortBy = element.getSortBy();
                break;
            }                            
            if (i == 0) {
                sort.setPrev(element.getSortBy());
                sort.setNext(notesElements.get(i+1).getSortBy());
            } else if (i == notesElements.size() - 1) {
                sort.setPrev(notesElements.get(i-1).getSortBy());
                sort.setNext(element.getSortBy());
            } else {
                sort.setPrev(notesElements.get(i-1).getSortBy());
                sort.setNext(notesElements.get(i+1).getSortBy());
            }
            if (element.getSortBy() > maxSortBy || maxSortBy == -1)
                maxSortBy = element.getSortBy();
            if (element.getSortBy() < minSortBy || minSortBy == -1)
                minSortBy = element.getSortBy();
        }
        first.setValue(maxSortBy);
        last.setValue(minSortBy);
    }
        
}
