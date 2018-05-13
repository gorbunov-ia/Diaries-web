package ru.gorbunov.diaries.service;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import ru.gorbunov.diaries.controller.dto.NoteElementDto;
import ru.gorbunov.diaries.controller.vm.SortElementVm;
import ru.gorbunov.diaries.domain.Movable;
import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.exception.SwapElementException;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Test for NoteElementServiceImpl class.
 *
 * @author Gorbunov.ia
 */
public class NoteElementServiceTest {

    /**
     * Const.
     */
    private static final Integer NOTE_ELEMENT_ID = 150;
    /**
     * Const for prev tests.
     */
    private static final Integer NEW_SORT_BY = 10;
    /**
     * Const for prev tests.
     */
    private static final Integer OLD_SORT_BY = 15;
    /**
     * Const for next tests.
     */
    private static final Integer NEW_NEXT_SORT_BY = 20;
    /**
     * Const for next tests.
     */
    private static final Integer MIDDLE_NEXT_SORT_BY = 15;
    /**
     * Const for next tests.
     */
    private static final Integer OLD_NEXT_SORT_BY = 10;

    /**
     * Main service for test.
     */
    private NoteElementService service;
    /**
     * Mock repository.
     */
    @Mock
    private NoteElementRepository noteElementRepository;
    /**
     * Original specification.
     */
    private NoteElementSpecification noteElementSpecification = new NoteElementSpecification();
    /**
     * Mock user service.
     */
    @Mock
    private UserService userService;

    /**
     * Standard junit initialize method.
     */
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        service = new NoteElementServiceImpl(noteElementRepository,  noteElementSpecification, userService);
    }

    /**
     * Test when user without authorization.
     */
    @Test
    public void testWithoutUserChangeSortBy() {
        //mockUser();
        mockNoteElementFindOne(getNoteElementForTest());
        Assertions.assertThatThrownBy(() -> service.changeSortBy(NOTE_ELEMENT_ID, NEW_SORT_BY))
                .isInstanceOf(SwapElementException.class);
    }

    /**
     * Test when note element id is not found in db.
     */
    @Test
    public void testNotFoundNoteElementChangeSortBy() {
        mockUser();
        Assertions.assertThatThrownBy(() -> service.changeSortBy(NOTE_ELEMENT_ID, NEW_SORT_BY))
                .isInstanceOf(SwapElementException.class);
    }

    /**
     * Test when note element is null.
     */
    @Test(expected = NullPointerException.class)
    public void testEmptyNoteElementChangeSortBy() {
        service.changeSortBy(null, NEW_SORT_BY);
    }

    /**
     * Test when new sort by is null.
     */
    @Test(expected = NullPointerException.class)
    public void testEmptySortByChangeSortBy() {
        service.changeSortBy(NOTE_ELEMENT_ID, null);
    }

    /**
     * Test when note element with new sort by not found in db.
     */
    @Test
    public void testNotFoundNewSortByChangeSortBy() {
        mockUser();

        NoteElement noteElement = getNoteElementForTest();
        mockNoteElementFindOne(noteElement);

        Assertions.assertThatThrownBy(() -> service.changeSortBy(NOTE_ELEMENT_ID, NEW_SORT_BY))
                .isInstanceOf(SwapElementException.class);
        Assert.assertEquals(noteElement.getSortBy(), OLD_SORT_BY);
    }

    /**
     * Test when new sort by is previous note element.
     */
    @Test
    public void testPrevSortByChangeSortBy() {
        mockUser();

        NoteElement noteElement = getNoteElementForTest();
        mockNoteElementFindOne(noteElement);

        NoteElement previous = new NoteElement();
        previous.setSortBy(NEW_SORT_BY);
        mockNoteElementFindAll(Collections.singletonList(previous));

        Collection<NoteElement> result = service.changeSortBy(NOTE_ELEMENT_ID, NEW_SORT_BY);

        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals(noteElement.getSortBy(), NEW_SORT_BY);

        final NoteElement noteElementAfterSwap = findNoteElementByIdFromCollection(result, noteElement.getId());
        Assert.assertEquals(noteElementAfterSwap.getSortBy(), NEW_SORT_BY);
        Assert.assertEquals(noteElement, noteElementAfterSwap);
        Assert.assertEquals(previous.getSortBy(), OLD_SORT_BY);
    }

    /**
     * Test with shift to next note elements.
     */
    @Test
    public void testNextShiftChangeSortBy() {
        mockUser();

        NoteElement noteElement = getNoteElementForTest();
        noteElement.setSortBy(OLD_NEXT_SORT_BY);
        mockNoteElementFindOne(noteElement);

        NoteElement middle = new NoteElement();
        middle.setId(NOTE_ELEMENT_ID + MIDDLE_NEXT_SORT_BY);
        middle.setSortBy(MIDDLE_NEXT_SORT_BY);
        NoteElement next = new NoteElement();
        next.setId(NOTE_ELEMENT_ID + NEW_NEXT_SORT_BY);
        next.setSortBy(NEW_NEXT_SORT_BY);
        mockNoteElementFindAll(Arrays.asList(next, middle));

        Collection<NoteElement> result = service.changeSortBy(NOTE_ELEMENT_ID, NEW_NEXT_SORT_BY);

        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals(noteElement.getSortBy(), NEW_NEXT_SORT_BY);

        final NoteElement noteElementAfterSwap = findNoteElementByIdFromCollection(result, noteElement.getId());
        Assert.assertEquals(noteElementAfterSwap.getSortBy(), NEW_NEXT_SORT_BY);
        Assert.assertEquals(noteElement, noteElementAfterSwap);

        Assert.assertEquals(middle.getSortBy(), OLD_NEXT_SORT_BY);
        Assert.assertEquals(middle, findNoteElementByIdFromCollection(result, middle.getId()));

        Assert.assertEquals(next.getSortBy(), MIDDLE_NEXT_SORT_BY);
        Assert.assertEquals(next, findNoteElementByIdFromCollection(result, next.getId()));
    }

    /**
     * Method help to find element by id from collection.
     *
     * @param noteElements collections for search
     * @param id note element id for search
     * @return NoteElement not null
     */
    private NoteElement findNoteElementByIdFromCollection(Collection<NoteElement> noteElements, Integer id) {
        Optional<NoteElement> result = noteElements.stream()
                .filter(element -> element.getId().equals(id))
                .findFirst();
        Assert.assertTrue(result.isPresent());
        return result.get();
    }

    /**
     * Mock get user method.
     */
    private void mockUser() {
        Mockito.when(userService.getUser()).thenReturn(Optional.of(new User()));
    }

    /**
     * Mock find one method for repository.
     *
     * @param noteElement mock note element
     */
    private void mockNoteElementFindOne(NoteElement noteElement) {
        Mockito.when(noteElementRepository.findOne(Mockito.any(Specification.class)))
                .thenReturn(Optional.of(noteElement));
    }

    /**
     * Create mock note element.
     *
     * @return mock element with data
     */
    private NoteElement getNoteElementForTest() {
        NoteElement result = new NoteElement();
        result.setId(NOTE_ELEMENT_ID);
        Note note = new Note();
        note.setId(1);
        result.setNote(note);
        result.setSortBy(OLD_SORT_BY);
        return result;
    }

    /**
     * Mock find all method for repository.
     *
     * @param noteElements list of note element mocks
     */
    private void mockNoteElementFindAll(List<NoteElement> noteElements) {
        Mockito.when(noteElementRepository.findAll(Mockito.any(Specification.class), Mockito.any(Sort.class)))
                .thenReturn(noteElements);
    }

    /**
     * Check fill sort element to empty list of movables.
     */
    @Test
    public void testEmptyFillSortElement() {
        //no exception
        service.fillSortElement(Collections.emptyList());
    }

    /**
     * Check fill sort element to list of one movable.
     */
    @Test
    public void testOneElementFillSortElement() {
        final int initialSortBy = 10;
        final Movable movable = createMovableWithSortBy(initialSortBy);

        service.fillSortElement(Collections.singletonList(movable));

        checkSortElementVm(movable.getSortElementVm(), initialSortBy, initialSortBy, initialSortBy, initialSortBy);
    }

    /**
     * Check fill sort element to full list of movable.
     */
    @Test
    public void testFullFillSortElement() {
        final int firstSortBy = 50;
        final Movable firstMovable = createMovableWithSortBy(firstSortBy);
        final int secondSortBy = 40;
        final Movable secondMovable = createMovableWithSortBy(secondSortBy);
        final int thirdSortBy = 20;
        final Movable thirdMovable = createMovableWithSortBy(thirdSortBy);
        final int fourthSortBy = 10;
        final Movable fourthMovable = createMovableWithSortBy(fourthSortBy);

        service.fillSortElement(Arrays.asList(firstMovable, secondMovable, thirdMovable, fourthMovable));

        checkSortElementVm(firstMovable.getSortElementVm(), firstSortBy, firstSortBy, secondSortBy, fourthSortBy);
        checkSortElementVm(secondMovable.getSortElementVm(), firstSortBy, firstSortBy, thirdSortBy, fourthSortBy);
        checkSortElementVm(thirdMovable.getSortElementVm(), firstSortBy, secondSortBy, fourthSortBy, fourthSortBy);
        checkSortElementVm(fourthMovable.getSortElementVm(), firstSortBy, thirdSortBy, fourthSortBy, fourthSortBy);
    }

    /**
     * Method to create Movable element and set sort by value.
     *
     * @param sortBy sorting value
     * @return movable object
     */
    private Movable createMovableWithSortBy(final int sortBy) {
        final NoteElementDto result = new NoteElementDto();
        result.setSortBy(sortBy);
        return result;
    }

    /**
     * Method to check sort element.
     *
     * @param sortElementVm target to check
     * @param first first value of target
     * @param prev prev value of target
     * @param next next value of target
     * @param last last value of target
     */
    private void checkSortElementVm(SortElementVm sortElementVm, int first, int prev, int next, int last) {
        Assert.assertNotNull(sortElementVm);
        Assert.assertEquals(sortElementVm.getFirst().intValue(), first);
        Assert.assertEquals(sortElementVm.getPrev().intValue(), prev);
        Assert.assertEquals(sortElementVm.getNext().intValue(), next);
        Assert.assertEquals(sortElementVm.getLast().intValue(), last);

    }

}
