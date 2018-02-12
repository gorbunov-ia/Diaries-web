package ru.gorbunov.diaries.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.mutable.MutableInt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ru.gorbunov.diaries.controller.vm.SortElementVm;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.domain.Movable;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.exception.SwapElementException;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;

/**
 * Implementation of service for interaction with note elements.
 *
 * @author Gorbunov.ia
 */
@Service
public class NoteElementServiceImpl implements NoteElementService {

    /**
     * Logger for class.
     */
    private final Logger log = LoggerFactory.getLogger(NoteElementServiceImpl.class);

    /**
     * Repository for Note Elements.
     */
    private final NoteElementRepository noteElementRepository;

    /**
     * Specification for Note Elements.
     */
    private final NoteElementSpecification noteElementSpecification;

    /**
     * Service for interaction with user.
     */
    private final UserService userService;

    /**
     * Base constructor.
     *
     * @param repository    repository for crud operation with db
     * @param specification specification for add condition into query to db
     * @param userService   service for interaction with user
     */
    public NoteElementServiceImpl(final NoteElementRepository repository,
                                  final NoteElementSpecification specification,
                                  final UserService userService) {
        this.noteElementRepository = repository;
        this.noteElementSpecification = specification;
        this.userService = userService;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public NoteElement changeSortBy(final Integer noteElementId, final Integer sortBy) {
        if (noteElementId == null || sortBy == null) {
            throw new IllegalArgumentException("Arguments must be not null.");
        }
        final User user = userService.getUser();
        if (user == null) {
            return null;
        }
        try {
            final NoteElement noteElement = getNoteElementByIdAndUser(noteElementId, user);
            if (noteElement == null) {
                throw new SwapElementException();
            }
            if (noteElement.getSortBy().equals(sortBy)) {
                return noteElement;
            }
            final List<NoteElement> noteElementsForShift = getNoteElementsForShift(noteElement, sortBy);
            if (noteElementsForShift.isEmpty()) {
                throw new SwapElementException();
            }
            if (!noteElementsContainSortBy(noteElementsForShift, sortBy)) {
                throw new SwapElementException();
            }

            final Integer oldSortBy = noteElement.getSortBy();
            prepareNoteElementsForShift(noteElementsForShift, noteElement);
            saveNoteElementsForShift(noteElementsForShift, noteElement);
            swapNoteElements(noteElementsForShift, noteElement, oldSortBy, sortBy);

            saveNoteElementsForShift(noteElementsForShift, noteElement);
            return noteElement;
        } catch (Exception e) {
            log.warn("Swap error ID: {}, sortBy: {}", noteElementId, sortBy);
            return null;
        }
    }

    /**
     * Select note element by id and user object from db.
     *
     * @param noteElementId note element id in db
     * @param user          note owner
     * @return              note element
     */
    private NoteElement getNoteElementByIdAndUser(final Integer noteElementId, final User user) {
        return noteElementRepository.findOne(Specifications.where(noteElementSpecification.byUser(user))
                .and(noteElementSpecification.byId(noteElementId)));
    }

    /**
     * Select note elements between old sort by and new sort by for shift from db.
     *
     * @param noteElement   note element for change sort by
     * @param newSortBy     new sort by value
     * @return              list of note elements
     */
    private List<NoteElement> getNoteElementsForShift(final NoteElement noteElement, final Integer newSortBy) {
        Specification<NoteElement> spec = noteElementSpecification.byNote(noteElement.getNote().getId());
        if (noteElement.getSortBy() <= newSortBy) {
            //Up order
            spec = Specifications.where(spec)
                    .and(noteElementSpecification.byRangeSortBy(noteElement.getSortBy() + 1, newSortBy))
                    .and(noteElementSpecification.orderBy("sortBy", true));
        } else {
            //Down order
            spec = Specifications.where(spec)
                    .and(noteElementSpecification.byRangeSortBy(newSortBy, noteElement.getSortBy() - 1))
                    .and(noteElementSpecification.orderBy("sortBy", false));
        }
        return noteElementRepository.findAll(spec);
    }

    /**
     * Search note element with sort by in list.
     *
     * @param noteElements  list for search
     * @param sortBy        target sort by
     * @return              true if contains
     */
    private boolean noteElementsContainSortBy(final List<NoteElement> noteElements, final Integer sortBy) {
        for (NoteElement element : noteElements) {
            if (element.getSortBy().equals(sortBy)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prepare list of note elements to shift.
     *
     * @param noteElementsForShift  note elements with old sort by note element
     * @param noteElement           note element for change sort by
     */
    private void prepareNoteElementsForShift(final List<NoteElement> noteElementsForShift,
                                             final NoteElement noteElement) {
        //Stupid MySQL constraint
        for (NoteElement element : noteElementsForShift) {
            element.setSortBy(element.getSortBy() * (-1));
        }
        noteElement.setSortBy(noteElement.getSortBy() * (-1));
    }

    /**
     * Save shift objects to db.
     *
     * @param noteElementsForShift  shifted note elements
     * @param noteElement           note element for new sort by
     */
    private void saveNoteElementsForShift(final List<NoteElement> noteElementsForShift, final NoteElement noteElement) {
        noteElementRepository.save(noteElement);
        noteElementRepository.save(noteElementsForShift);
        noteElementRepository.flush();
    }

    /**
     * Change sort by values for target note element and shifted note elements.
     *
     * @param noteElementsForShift  shifted note elements
     * @param noteElement           note element with new sort by
     * @param oldSortBy             old sort by note element value
     * @param newSortBy             new sort by note element value
     */
    private void swapNoteElements(final List<NoteElement> noteElementsForShift, final NoteElement noteElement,
                                  final Integer oldSortBy, final  Integer newSortBy) {
        //Swap elements
        for (int i = 0; i < noteElementsForShift.size() - 1; i++) {
            Integer itemSortBy = noteElementsForShift.get(i).getSortBy();
            Integer nextItemSortBy = noteElementsForShift.get(i + 1).getSortBy();
            noteElementsForShift.get(i).setSortBy(nextItemSortBy * (-1));
            noteElementsForShift.get(i + 1).setSortBy(itemSortBy);
        }
        //Last swap
        noteElementsForShift.get(noteElementsForShift.size() - 1).setSortBy(oldSortBy);
        noteElement.setSortBy(newSortBy);
        noteElement.setLastModified(new Date());
    }

    @Override
    public void fillSortElement(final List<? extends Movable> movables) {
        MutableInt first = new MutableInt();
        MutableInt last = new MutableInt();
        int maxSortBy = -1;
        int minSortBy = -1;
        for (int i = 0; i < movables.size(); i++) {
            Movable element = movables.get(i);
            SortElementVm sort = new SortElementVm();
            element.setSortElementVm(sort);
            sort.setFirst(first);
            sort.setLast(last);
            if (movables.size() == 1) {
                sort.setPrev(element.getSortBy());
                sort.setNext(element.getSortBy());
                minSortBy = element.getSortBy();
                maxSortBy = element.getSortBy();
                break;
            }
            if (i == 0) {
                sort.setPrev(element.getSortBy());
                sort.setNext(movables.get(i + 1).getSortBy());
            } else if (i == movables.size() - 1) {
                sort.setPrev(movables.get(i - 1).getSortBy());
                sort.setNext(element.getSortBy());
            } else {
                sort.setPrev(movables.get(i - 1).getSortBy());
                sort.setNext(movables.get(i + 1).getSortBy());
            }
            if (element.getSortBy() > maxSortBy || maxSortBy == -1) {
                maxSortBy = element.getSortBy();
            }
            if (element.getSortBy() < minSortBy || minSortBy == -1) {
                minSortBy = element.getSortBy();
            }
        }
        first.setValue(maxSortBy);
        last.setValue(minSortBy);
    }

    @Override
    public List<NoteElement> getUserNoteElementsByNoteWithSort(Integer noteId, String field, boolean isDesc) {
        final User user = userService.getUser();
        if (user == null) {
            return Collections.emptyList();
        }
        return noteElementRepository.findAll(Specifications.where(noteElementSpecification.byUser(user))
                .and(noteElementSpecification.byNote(noteId)).and(noteElementSpecification.orderBy(field, isDesc)));
    }
}
