package ru.gorbunov.diaries.converters;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.gorbunov.diaries.controller.vm.NoteElementDto;
import ru.gorbunov.diaries.domain.NoteElement;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

/**
 * Test for NoteElementToNoteElementDtoConverter class.
 *
 * @author Gorbunov.ia
 */
@SpringBootTest
public class NoteElementToNoteElementDtoConverterTest {

    /**
     * Error message.
     */
    private static final String CONVERT_ERR_MSG = "Data lost when converted NoteElement to NoteElementDto";

    /**
     * Converter instance.
     */
    private final NoteElementToNoteElementDtoConverter converter = new NoteElementToNoteElementDtoConverter();

    /**
     * Test convert empty Note Element object.
     */
    @Test
    public void testConvertEmptyObject() {
        NoteElementDto noteElementDto = converter.convert(new NoteElement());
        Assert.assertNotNull("NoteElementDto object is null, but NoteElement not null.", noteElementDto);
    }

    /**
     * Test convert Note Element object with filled field.
     */
    @Test
    public void testConvertFullObject() {
        final Integer noteElementId = new Random().nextInt();
        final NoteElement noteElement = getTestNoteElement(noteElementId);
        final NoteElementDto noteElementDto = converter.convert(noteElement);

        Assert.assertEquals(CONVERT_ERR_MSG, noteElementId, noteElementDto.getId());
        Assert.assertEquals(CONVERT_ERR_MSG, "unitTestNoteElement", noteElementDto.getDescription());
        Assert.assertEquals(CONVERT_ERR_MSG, Integer.valueOf(1), noteElementDto.getSortBy());
        Assert.assertEquals(CONVERT_ERR_MSG, Date.from(Instant.EPOCH), noteElementDto.getLastModified());
    }

    /**
     * Test convert null Note Element object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertNullSource() {
        converter.convert(null);
    }

    /**
     * Help method for creating test note element object.
     *
     * @param noteElementId note element id
     * @return              note element object
     */
    private NoteElement getTestNoteElement(final Integer noteElementId) {
        NoteElement noteElement = new NoteElement();
        noteElement.setId(noteElementId);
        noteElement.setDescription("unitTestNoteElement");
        noteElement.setSortBy(1);
        noteElement.setLastModified(Date.from(Instant.EPOCH));
        return noteElement;
    }

}
