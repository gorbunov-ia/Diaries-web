package ru.gorbunov.diaries.controller.vm;

import java.io.Serializable;

/**
 * User Data Transfer Object.
 *
 * @author Gorbunov.ia
 */
public class UserDto implements Serializable {

    /**
     * Id entity.
     */
    private Integer id;

    /**
     * User login.
     */
    private String login;

    /**
     * User email.
     */
    private String email;

    /**
     * Indicator of user activate.
     */
    private Boolean isActive = true;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

}
