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

//    @Value("${validation.user.username.length}")
//    private static int VALID_USERNAME_LENGTH;
//    @Value("${validation.user.password.length}")
//    private static int VALID_PASSWORD_LENGTH;

//    private UserValidator userValidation;

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

//    @Transient
//    @Autowired
//    public void setUserValidation(UserValidator userValidation) {
//        this.userValidation = userValidation;
//    }

//    /**
//     * Validates that user instance is currently in a valid state.
//     *
//     * @throws UserValidationException in case the user is not in valid state.
//     */
//    @Override
//    public void validate() throws ValidationException {
//        validateUsername();
//        validatePassword();
//    }
//
//    private void validateUsername() throws UserValidationException {
//        if (StringUtils.isBlank(username)) throw new UserValidationException("Username is a required field");
//        if (username.length() != userValidation.getUserNameLength())
//            throw new UserValidationException("Username must be " + userValidation.getUserNameLength() + " chars long");
//        if (!StringUtils.isAlphanumeric(username))
//            throw new UserValidationException("Username can contain only alphanumeric chars");
//
//    }
//
//    private void validatePassword() throws UserValidationException {
//        if (StringUtils.isBlank(password)) throw new UserValidationException("Password is a required field");
//        if (password.length() != userValidation.getPasswordLength())
//            throw new UserValidationException("Password must be " + userValidation.getPasswordLength() + " digits");
//        if (!StringUtils.isNumeric(password))
//            throw new UserValidationException("Password can contain only digits");
//    }

    /*
    ########### Spring Security ##################
     */

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = getRoles();
        Set<SimpleGrantedAuthority> authorities = new LinkedHashSet<>(roles.size());
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        System.out.println("User.getAuthorities roles=" + roles.iterator().next());
        return authorities;
//        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
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
     * -- Role may be attached to multiple users, User may have multiple roles
     * thus the ManyToMany
     *
     * @return
     */
    @ManyToMany(fetch = FetchType.EAGER)
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
