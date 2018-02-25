package ru.gorbunov.diaries.converters;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.gorbunov.diaries.controller.dto.NoteDto;
import ru.gorbunov.diaries.domain.Note;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

/**
 * Test for NoteToNoteDtoConverter class.
 *
 * @author Gorbunov.ia
 */
@SpringBootTest
public class NoteToNoteDtoConverterTest {

    /**
     * Error message.
     */
    private static final String CONVERT_ERR_MSG = "Data lost when converted Note to NoteDto";

    /**
     * Converter instance.
     */
    private final NoteToNoteDtoConverter converter = new NoteToNoteDtoConverter();

    /**
     * Test convert empty Note object.
     */
    @Test
    public void testConvertEmptyObject() {
        NoteDto noteDto = converter.convert(new Note());
        Assert.assertNotNull("NoteDto object is null, but Note not null.", noteDto);
    }

    /**
     * Test convert Note object with filled field.
     */
    @Test
    public void testConvertFullObject() {
        final Integer noteId = new Random().nextInt();
        final Note note = getTestNote(noteId);
        final NoteDto noteDto = converter.convert(note);

        Assert.assertEquals(CONVERT_ERR_MSG, noteId, noteDto.getId());
        Assert.assertEquals(CONVERT_ERR_MSG, "unitTestNote", noteDto.getDescription());
        Assert.assertEquals(CONVERT_ERR_MSG, Integer.valueOf(1), noteDto.getSortBy());
        Assert.assertEquals(CONVERT_ERR_MSG, Date.from(Instant.EPOCH), noteDto.getLastModified());
    }

    /**
     * Test convert null Note object.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConvertNullSource() {
        converter.convert(null);
    }

    /**
     * Help method for creating test note object.
     *
     * @param noteId    note id
     * @return          note object
     */
    private Note getTestNote(final Integer noteId) {
        Note note = new Note();
        note.setId(noteId);
        note.setDescription("unitTestNote");
        note.setSortBy(1);
        note.setLastModified(Date.from(Instant.EPOCH));
        return note;
    }

}
