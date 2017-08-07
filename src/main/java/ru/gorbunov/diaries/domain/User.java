package ru.gorbunov.diaries.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Gorbunov.ia
 */
@Entity
@Table(name = "t_Users")
public class User implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
        
    @NotNull
    @Size(min = 3, max = 32)
    @Column(unique = true, nullable = false, length = 32)
    private String login;
    
    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "Pswrd", nullable = false, length = 60)
    private String password;
    
    @NotNull
    @Email
    @Column(nullable = false, length = 255)
    private String email;
        
    private Boolean isActive = true;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "t_UsersRoles",
        joinColumns = {@JoinColumn(name = "UserID", referencedColumnName = "ID")},
        inverseJoinColumns = {@JoinColumn(name = "RoleID", referencedColumnName = "ID")})
    private Set<Role> roles = new HashSet<>();
    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        return Objects.equals(this.id, other.id);
    }
        
}
