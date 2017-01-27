package org.zelenikr.pia.domain;

import org.zelenikr.pia.verification.MessageRecipient;

import javax.persistence.*;


/**
 * Entity representing person relative to bank. For example client or employee.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_person")
public class Person extends User implements MessageRecipient {

    protected String name;

    protected String surname;
    /**
     * Personal identification number
     */
    protected String personalIdNumber;

    protected String email;

    protected String phoneNumber;

    private Address address;

    public Person() {
        super();
    }

    public Person(String name, String surname, String personalIdNumber, String phoneNumber, String email) {
        super();
        this.email = email;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.personalIdNumber = personalIdNumber;
        this.name = name;
    }

    /*
    ########### API ##################
     */

    @Override
    public String displayName() {
        return getName() + " " + getSurname();
    }

    @Transient
    @Override
    public String getEmailAddress() {
        return getEmail();
    }

    @Transient
    @Override
    public String getMobileNumber() {
       return getPhoneNumber();
    }

    /*
    ########### MAPPINGS #####################
     */

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(nullable = false)
    public String getPersonalIdNumber() {
        return personalIdNumber;
    }

    public void setPersonalIdNumber(String personalIdNumber) {
        this.personalIdNumber = personalIdNumber;
    }

    @Column(nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * OneToOne association between person and address.
     *
     * @return
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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
