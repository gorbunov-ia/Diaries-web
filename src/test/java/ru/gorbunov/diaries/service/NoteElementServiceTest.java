package ru.gorbunov.diaries.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import ru.gorbunov.diaries.domain.Note;
import ru.gorbunov.diaries.domain.NoteElement;
import ru.gorbunov.diaries.domain.User;
import ru.gorbunov.diaries.repository.NoteElementRepository;
import ru.gorbunov.diaries.repository.specification.NoteElementSpecification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Test for NoteElementServiceImpl class.
 *
 * @author Gorbunov.ia
 */
@SpringBootTest
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
        Assert.assertNull(service.changeSortBy(NOTE_ELEMENT_ID, NEW_SORT_BY));
    }

    /**
     * Test when note element id is not found in db.
     */
    @Test
    public void testNotFoundNoteElementChangeSortBy() {
        mockUser();
        Assert.assertNull(service.changeSortBy(NOTE_ELEMENT_ID, NEW_SORT_BY));
    }

    /**
     * Test when note element is null.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyNoteElementChangeSortBy() {
        service.changeSortBy(null, NEW_SORT_BY);
    }

    /**
     * Test when new sort by is null.
     */
    @Test(expected = IllegalArgumentException.class)
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

        Assert.assertNull(service.changeSortBy(NOTE_ELEMENT_ID, NEW_SORT_BY));
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

        NoteElement result = service.changeSortBy(NOTE_ELEMENT_ID, NEW_SORT_BY);

        Assert.assertEquals(noteElement.getSortBy(), NEW_SORT_BY);
        Assert.assertEquals(result.getSortBy(), NEW_SORT_BY);
        Assert.assertEquals(noteElement, result);
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
        middle.setSortBy(MIDDLE_NEXT_SORT_BY);
        NoteElement next = new NoteElement();
        next.setSortBy(NEW_NEXT_SORT_BY);
        mockNoteElementFindAll(Arrays.asList(next, middle));

        NoteElement result = service.changeSortBy(NOTE_ELEMENT_ID, NEW_NEXT_SORT_BY);

        Assert.assertEquals(noteElement.getSortBy(), NEW_NEXT_SORT_BY);
        Assert.assertEquals(result.getSortBy(), NEW_NEXT_SORT_BY);
        Assert.assertEquals(noteElement, result);

        Assert.assertEquals(middle.getSortBy(), OLD_NEXT_SORT_BY);
        Assert.assertEquals(next.getSortBy(), MIDDLE_NEXT_SORT_BY);
    }

    /**
     * Mock get user method.
     */
    private void mockUser() {
        Mockito.when(userService.getUser()).thenReturn(new User());
    }

    /**
     * Mock find one method for repository.
     *
     * @param noteElement mock note element
     */
    private void mockNoteElementFindOne(NoteElement noteElement) {
        Mockito.when(noteElementRepository.findOne(Mockito.any(Specification.class)))
                .thenReturn(noteElement);
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
        Mockito.when(noteElementRepository.findAll(Mockito.any(Specification.class)))
                .thenReturn(noteElements);
    }

}
