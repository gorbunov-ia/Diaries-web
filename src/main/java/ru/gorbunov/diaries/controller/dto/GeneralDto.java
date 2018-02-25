package ru.gorbunov.diaries.controller.dto;

import java.io.Serializable;

/**
 * Data Transfer Object.
 *
 * @author Gorbunov.ia
 */
public class GeneralDto implements Serializable {

    /**
     * Id entity.
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
