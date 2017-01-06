package org.zelenikr.pia.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.exception.PersonValidationException;
import org.zelenikr.pia.validation.PersonValidation;
import org.zelenikr.pia.validation.ValidationException;

import javax.persistence.*;


/**
 * Entity representing person relative to bank. For example client or employee.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_person")
public class Person extends User {

    private PersonValidation personValidation;

    protected String name;

    protected String surname;
    /**
     * Personal identification number
     */
    protected Integer personalIdNumber;

    protected String email;
    /**
     * Phone number without prefix (like "777666555")
     */
    protected Integer phoneNumber;

    private Address address;

    public Person() {
        super();
    }

    public Person(String username, String password, String name, String surname, int personalIdNumber, int phoneNumber, String email) {
        super(username, password);
        this.email = email;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.personalIdNumber = personalIdNumber;
        this.name = name;
    }

    /*
    ########### API ##################
     */

    @Transient
    @Autowired
    public void setPersonValidation(PersonValidation personValidation) {
        this.personValidation = personValidation;
    }

    /**
     * Validates that person instance is currently in a valid state.
     *
     * @throws PersonValidationException in case the person is not in valid state.
     */
    @Override
    public void validate() throws ValidationException {
        super.validate();
        validateName();
        validateSurname();
        validatePersonalIdNumber();
        validateEmail();
    }

    private void validateName() throws PersonValidationException {
        if (StringUtils.isBlank(name)) throw new PersonValidationException("Name is a required field");
        if (!StringUtils.isAlpha(name)) throw new PersonValidationException("Name can contain only Unicode letters");
    }

    private void validateSurname() throws PersonValidationException {
        if (StringUtils.isBlank(surname)) throw new PersonValidationException("Surname is a required field");
        if (!StringUtils.isAlpha(surname))
            throw new PersonValidationException("Surname can contain only Unicode letters");
    }

    private void validatePersonalIdNumber() throws PersonValidationException {
        if (personalIdNumber == null)
            throw new PersonValidationException("Personal identification number is required field");
        if (personalIdNumber.toString().length() < personValidation.getPersonIdNoMinLength() ||
                personalIdNumber.toString().length() > personValidation.getPersonIdNoMaxLength())
            throw new PersonValidationException("Personal identification number's length must be in the interval <"
                    + personValidation.getPersonIdNoMinLength() + ";" + personValidation.getPersonIdNoMaxLength() + ">");
    }

    private void validateEmail() throws PersonValidationException {
        if (personalIdNumber == null) throw new PersonValidationException("Email is required field");
        if (!EmailValidator.getInstance().isValid(email))
            throw new PersonValidationException("Email is invalid");
    }

    /*
    ########### MAPPINGS #####################
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "personal_id_number")
    public Integer getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(Integer personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone_number")
    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @ManyToOne
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        if (!super.equals(o)) return false;

        Client client = (Client) o;

        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (surname != null ? !surname.equals(client.surname) : client.surname != null) return false;
        if (personalIdNumber != null ? !personalIdNumber.equals(client.personalIdNumber) : client.personalIdNumber != null)
            return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        return phoneNumber != null ? phoneNumber.equals(client.phoneNumber) : client.phoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (personalIdNumber != null ? personalIdNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{");
        sb.append("name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", personalIdNumber=").append(personalIdNumber);
        sb.append(", email='").append(email).append('\'');
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append('}');
        return sb.toString();
    }
}
