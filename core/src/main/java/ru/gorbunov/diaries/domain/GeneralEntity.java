package ru.gorbunov.diaries.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * General JPA entity.
 *
 * @author Gorbunov.ia
 */
@MappedSuperclass
public class GeneralEntity {

    /**
     * Id entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
