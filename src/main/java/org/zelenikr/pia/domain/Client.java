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

    private Set<BankAccount> bankAccounts;

    private Set<PatternPaymentOrder> paymentOrderPatterns;

    public Client() {
        super();
    }

    public Client(String username, String password, String name, String surname, String personalIdNumber, String phoneNumber, String email) {
        super(username, password, name, surname, personalIdNumber, phoneNumber, email);
    }

    /*
    ########### MAPPINGS #####################
     */

    /**
     * OneToMany association between client and bank accounts.
     *
     * @return
     */
    @OneToMany(mappedBy = "owner")
    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    /**
     * OneToMany association between client and payment order patterns.
     *
     * @return
     */
    @OneToMany(mappedBy = "owner")
    public Set<PatternPaymentOrder> getPaymentOrderPatterns() {
        return paymentOrderPatterns;
    }

    public void setPaymentOrderPatterns(Set<PatternPaymentOrder> paymentOrderPatterns) {
        this.paymentOrderPatterns = paymentOrderPatterns;
    }
}
