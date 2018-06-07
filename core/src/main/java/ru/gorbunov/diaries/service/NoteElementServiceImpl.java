package ru.gorbunov.diaries.service;

import org.apache.commons.lang3.mutable.MutableInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.gorbunov.diaries.controller.dto.NoteElementDto;
import ru.gorbunov.diaries.controller.vm.SortElementVm;
import ru.gorbunov.diaries.domain.Movable;
import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.exception.BadRequestException;
import ru.gorbunov.diaries.exception.ResourceNotFoundException;
import ru.gorbunov.diaries.exception.SwapElementException;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;
import ru.gorbunov.diaries.service.helper.SwapHelper;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
     * Service for interaction with note.
     */
    private final NoteService noteService;

    /**
     * Base constructor.
     *
     * @param repository    repository for crud operation with db
     * @param specification specification for add condition into query to db
     * @param userService   service for interaction with user
     * @param noteService   service for interaction with note
     */
    public NoteElementServiceImpl(final NoteElementRepository repository, final NoteElementSpecification specification,
                                  final UserService userService, final NoteService noteService) {
        this.noteElementRepository = repository;
        this.noteElementSpecification = specification;
        this.userService = userService;
        this.noteService = noteService;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Collection<NoteElement> changeSortBy(final Integer noteElementId, final Integer sortBy) {
        if (noteElementId == null || sortBy == null) {
            throw new NullPointerException("Arguments must be not null.");
        }
        final Optional<User> user = userService.getUser();
        if (!user.isPresent()) {
            throw new SwapElementException();
        }
        final Optional<NoteElement> noteElement = getNoteElementByIdAndUser(noteElementId, user.get());
        if (!noteElement.isPresent()) {
            throw new SwapElementException();
        }
        if (noteElement.get().getSortBy().equals(sortBy)) {
            return Collections.singletonList(noteElement.get());
        }
        final List<NoteElement> noteElementsForShift = getNoteElementsForShift(noteElement.get(), sortBy);
        if (noteElementsForShift.isEmpty()) {
            throw new SwapElementException();
        }
        SwapHelper<NoteElement> swapHelper = new SwapHelper<>(noteElementsForShift, noteElement.get(), sortBy);
        return swapHelper.swap(noteElementRepository);
    }

    /**
     * Select note element by id and user object from db.
     *
     * @param noteElementId note element id in db
     * @param user          note owner
     * @return note element
     */
    private Optional<NoteElement> getNoteElementByIdAndUser(final Integer noteElementId, final User user) {
        return noteElementRepository.findOne(Specification.where(noteElementSpecification.byUser(user))
                .and(noteElementSpecification.byId(noteElementId)));
    }

    /**
     * Select note elements between old sort by and new sort by for shift from db.
     *
     * @param noteElement note element for change sort by
     * @param newSortBy   new sort by value
     * @return list of note elements
     */
    private List<NoteElement> getNoteElementsForShift(final NoteElement noteElement, final Integer newSortBy) {
        final Integer sortByFirst;
        final Integer sortByLast;
        final Sort.Direction sortDirection;
        if (noteElement.getSortBy() <= newSortBy) {
            //Up order
            sortByFirst = noteElement.getSortBy() + 1;
            sortByLast = newSortBy;
            sortDirection = Sort.Direction.DESC;
        } else {
            //Down order
            sortByFirst = newSortBy;
            sortByLast = noteElement.getSortBy() - 1;
            sortDirection = Sort.Direction.ASC;
        }
        final Specification<NoteElement> spec = Specification
                .where(noteElementSpecification.byNote(noteElement.getNote().getId()))
                .and(noteElementSpecification.byRangeSortBy(sortByFirst, sortByLast));
        return noteElementRepository.findAll(spec, Sort.by(new Sort.Order(sortDirection, "sortBy")));
    }

    @Override
    public void fillSortElement(final List<? extends Movable> movables) {
        if (movables == null || movables.isEmpty()) {
            return;
        }
        final int initialValueSortBy = -1;
        MutableInt first = new MutableInt();
        MutableInt last = new MutableInt();
        int maxSortBy = initialValueSortBy;
        int minSortBy = initialValueSortBy;
        for (int i = 0; i < movables.size(); i++) {
            Movable element = movables.get(i);
            SortElementVm sort = new SortElementVm();
            element.setSortElementVm(sort);
            sort.setFirst(first);
            sort.setPrev(movables.get(sort.getPrevIndexShift(i, movables.size())).getSortBy());
            sort.setNext(movables.get(sort.getNextIndexShift(i, movables.size())).getSortBy());
            sort.setLast(last);
            if (element.getSortBy() > maxSortBy || maxSortBy == initialValueSortBy) {
                maxSortBy = element.getSortBy();
            }
            if (element.getSortBy() < minSortBy || minSortBy == initialValueSortBy) {
                minSortBy = element.getSortBy();
            }
        }
        first.setValue(maxSortBy);
        last.setValue(minSortBy);
    }

    @Override
    public List<NoteElement> getUserNoteElementsByNoteWithSort(Integer noteId, String field, boolean isDesc) {
        return userService.getUser()
                .map(user -> noteElementRepository.findAll(Specification
                                .where(noteElementSpecification.byUser(user))
                                .and(noteElementSpecification.byNote(noteId)),
                        Sort.by(new Sort.Order(noteElementSpecification.getDirection(isDesc), field))))
                .orElseGet(Collections::emptyList);
    }

    @Override
    public NoteElement createNoteElement(NoteElementDto noteElementDto) {
        final Note note = noteService.getUserNoteById(noteElementDto.getNoteId())
                .orElseThrow(() -> new BadRequestException("Note was not found."));
        return noteElementRepository.save(getNoteElementFromDto(noteElementDto, note));
    }

    @Override
    public void deleteNoteElement(Integer noteElementId) {
        final Optional<NoteElement> noteElement = noteElementRepository.findOne(noteElementSpecification
                .byUser(userService.getUser().orElseThrow(BadRequestException::ofUser))
                .and(noteElementSpecification.byId(noteElementId)));
        noteElementRepository.delete(noteElement.orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public NoteElement updateNoteElement(NoteElementDto noteElementDto) {
        return null;
    }

    /**
     * Create note element entity and fill field from dto.
     *
     * @param noteElementDto dto
     * @param note           note for set into entity
     * @return not saved entity
     */
    private NoteElement getNoteElementFromDto(NoteElementDto noteElementDto, Note note) {
        final NoteElement noteElement = new NoteElement();
        noteElement.setNote(note);
        noteElement.setDescription(noteElementDto.getDescription());
        noteElement.setSortBy(getSortBy(note));
        noteElement.setLastModified(new Date());
        return noteElement;
    }

    /**
     * Return next sort by for current note.
     *
     * @param note note for search sort by values
     * @return next valid sort by value
     */
    private Integer getSortBy(Note note) {
        return Optional
                .ofNullable(noteElementRepository.getMaxSortByByNoteId(note.getId()))
                .orElse(0) + 1;
    }
}
