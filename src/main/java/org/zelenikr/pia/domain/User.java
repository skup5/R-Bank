package org.zelenikr.pia.domain;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Entity representing application user, who can sign in and sign out.
 *
 * @author Jakub Danek
 */
@Entity
@Table(name = "zelenikr_rbank_user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseObject implements UserDetails {

    /**
     * Login, unique
     */
    private String username;
    /**
     * Secret for signing-in
     */
    private String password;
    /**
     * Granted authority role
     */
    private Set<Role> roles;

    public User() {
        this.roles = new LinkedHashSet<>();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    /*
    ########### API ##################
     */

    public String displayName(){
        return getUsername();
    }

    /*
    ########### Spring Security ##################
     */

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = getRoles();
        Set<SimpleGrantedAuthority> authorities = new LinkedHashSet<>(roles.size());
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getType().name()));
        }
        //System.out.println("User.getAuthorities roles=" + roles.iterator().next());
        return authorities;
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }

    /*
    ########### MAPPINGS #####################
     */

    @Column(unique = true, nullable = false)
    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * ManyToMany association between user and his roles.
     * <p>
     * -- Role may be attached to multiple users, Client may have multiple roles
     * thus the ManyToMany
     *
     * @return
     */
    @ManyToMany
    @JoinTable(name = "zelenikr_rbank_user_roles", joinColumns = @JoinColumn(name = "user", referencedColumnName = "username"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return !(username != null ? !username.equals(user.username) : user.username != null);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{");
        sb.append("username='").append(username).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
