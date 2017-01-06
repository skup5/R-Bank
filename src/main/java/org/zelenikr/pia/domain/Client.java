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
public class Client extends Person {

    @Column(name = "bank_accounts")
    private Set<BankAccount> bankAccounts;

    //private Set<SinglePaymentOrder> singlePaymentOrders;
    //private Set<PatternPaymentOrder> patternPaymentOrders;

    public Client() {
        super();
    }

    public Client(String username, String password, String name, String surname, int personalIdNumber, String email, int phoneNumber) {
       super(username, password, name, surname, personalIdNumber, phoneNumber, email);
    }

    /*
    ########### MAPPINGS #####################
     */

    @OneToMany(mappedBy = "owner")
    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

}
