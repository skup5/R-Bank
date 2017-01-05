package org.zelenikr.pia.domain;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity representing bank client.
 *
 * @author Roman Zelenik
 */
@Entity
@Table(name = "zelenikr_rbank_client")
public class Client extends User {

    private String name;
    private String surname;
    /**
     * Personal identification number
     */
    private Integer personalIdNumber;
    private String email;
    /**
     * Phone number without prefix (like "777666555")
     */
    private Integer phoneNumber;
    private Address address;
    private Set<BankAccount> bankAccounts;

    //private Set<SinglePaymentOrder> singlePaymentOrders;
    //private Set<PatternPaymentOrder> patternPaymentOrders;

    public Client() {
        super();
    }

    public Client(String username, String password, String name, String surname, int personalIdNumber, String email, int phoneNumber) {
        super(username, password);
        this.name = name;
        this.surname = surname;
        this.personalIdNumber = personalIdNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /*
    ########### API ##################
     */

    public void validate() {
        validateName();
        validateSurname();
        validatePersonalIdNumber();
        validateEmail();
    }

    private void validateName() {
        throw new UnsupportedOperationException();
    }

    private void validateSurname() {
        throw new UnsupportedOperationException();
    }

    private void validatePersonalIdNumber() {
        throw new UnsupportedOperationException();
    }

    private void validateEmail() {
        throw new UnsupportedOperationException();
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

    @OneToMany(cascade = CascadeType.ALL)
    @Column(name = "bank_accounts")
    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
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
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", personalIdNumber=").append(personalIdNumber);
        sb.append(", email='").append(email).append('\'');
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append('}');
        return sb.toString();
    }
}
