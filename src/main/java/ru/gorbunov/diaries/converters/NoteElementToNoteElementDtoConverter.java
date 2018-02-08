package ru.gorbunov.diaries.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.gorbunov.diaries.controller.vm.NoteElementDto;
import ru.gorbunov.diaries.domain.NoteElement;

/**
 * Converter NoteElement class to NoteElementDto class.
 *
 * @author Gorbunov.ia
 */
@Component
public class NoteElementToNoteElementDtoConverter implements Converter<NoteElement, NoteElementDto> {

    @Override
    public NoteElementDto convert(NoteElement source) {
        if (source == null) {
            throw new IllegalArgumentException("The source argument must to be NOT null.");
        }
        final NoteElementDto target = new NoteElementDto();
        target.setId(source.getId());
        target.setDescription(source.getDescription());
        target.setSortBy(source.getSortBy());
        target.setLastModified(source.getLastModified());
        return target;
    }

}
