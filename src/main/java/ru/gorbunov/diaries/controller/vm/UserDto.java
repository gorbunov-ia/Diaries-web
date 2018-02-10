package ru.gorbunov.diaries.controller.vm;

import java.util.Objects;

/**
 * User Data Transfer Object.
 *
 * @author Gorbunov.ia
 */
public class UserDto extends GeneralDto {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return Objects.equals(getId(), userDto.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId());
    }

}
