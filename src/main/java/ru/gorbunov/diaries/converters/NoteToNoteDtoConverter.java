package ru.gorbunov.diaries.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ru.gorbunov.diaries.controller.dto.NoteDto;
import ru.gorbunov.diaries.domain.Note;

/**
 * Converter Note class to NoteDto class.
 *
 * @author Gorbunov.ia
 */
@Component
public class NoteToNoteDtoConverter implements Converter<Note, NoteDto> {

    @Override
    public NoteDto convert(Note source) {
        if (source == null) {
            throw new IllegalArgumentException("The source argument must to be NOT null.");
        }
        final NoteDto target = new NoteDto();
        target.setId(source.getId());
        target.setDescription(source.getDescription());
        target.setSortBy(source.getSortBy());
        target.setLastModified(source.getLastModified());
        return target;
    }

}
