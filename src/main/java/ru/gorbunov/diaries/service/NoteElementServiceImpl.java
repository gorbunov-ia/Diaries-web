package ru.gorbunov.diaries.service;

import org.apache.commons.lang3.mutable.MutableInt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
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
public class NoteElementServiceImpl implements NoteElementService {
    
    private final Logger log = LoggerFactory.getLogger(NoteElementServiceImpl.class);
    
    private final NoteElementRepository noteElementRepository;
    
    private final NoteElementSpecification noteElementSpecification;
    
    private final NoteRepository noteRepository;

    private final NoteSpecification noteSpecification;

    public NoteElementServiceImpl(NoteElementRepository noteElementRepository,
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
            NoteElement noteElement = noteElementRepository.findOne(Specifications
                    .where(noteElementSpecification.byUser()).and(noteElementSpecification.byId(noteElementId)));

            if (noteElement == null
                    || noteElement.getSortBy().equals(sortBy))
                throw new SwapElementException();

            final Integer oldSortBy = noteElement.getSortBy();

            Specification<NoteElement> spec = noteElementSpecification.byNote(noteElement.getNote().getId());
            if (oldSortBy <= sortBy) {
                //Up order
                spec = Specifications.where(spec)
                        .and(noteElementSpecification.byRangeSortBy(oldSortBy + 1,sortBy))
                        .and(noteElementSpecification.orderBy("sortBy", true));
            } else {
                //Down order
                spec = Specifications.where(spec)
                        .and(noteElementSpecification.byRangeSortBy(sortBy,oldSortBy - 1))
                        .and(noteElementSpecification.orderBy("sortBy", false));
            }
            List<NoteElement> noteElementsForShift = noteElementRepository.findAll(spec);

            if (noteElementsForShift == null)
                throw new SwapElementException();

            boolean hasSortByItem = false;
            for (NoteElement element : noteElementsForShift) {
                if (element.getSortBy().equals(sortBy))
                    hasSortByItem = true;
                //Stupid MySQL constraint
                element.setSortBy(element.getSortBy() * (-1));
            }
            if (!hasSortByItem)
                throw new SwapElementException();

            //Stupid MySQL constraint
            noteElement.setSortBy(noteElement.getSortBy() * (-1));
            noteElementRepository.save(noteElement);
            noteElementRepository.save(noteElementsForShift);
            noteElementRepository.flush();
            //Swap elements
            for (int i = 0; i < noteElementsForShift.size() - 1; i++) {
                Integer itemSortBy = noteElementsForShift.get(i).getSortBy();
                Integer nextItemSortBy = noteElementsForShift.get(i+1).getSortBy();
                noteElementsForShift.get(i).setSortBy(nextItemSortBy * (-1));
                noteElementsForShift.get(i+1).setSortBy(itemSortBy);
            }
            //Last swap
            noteElementsForShift.get(noteElementsForShift.size() - 1).setSortBy(oldSortBy);
            noteElement.setSortBy(sortBy);
            noteElement.setLastModified(new Date());

            noteElementRepository.save(noteElementsForShift);
            noteElementRepository.save(noteElement);
            noteElementRepository.flush();
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
