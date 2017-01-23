package org.zelenikr.pia.domain;

import javax.persistence.*;
import java.util.LinkedHashSet;
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
        init();
    }

    public Client(String name, String surname, String personalIdNumber, String phoneNumber, String email) {
        super(name, surname, personalIdNumber, phoneNumber, email);
        init();
    }

    private void init() {
        this.bankAccounts = new LinkedHashSet<>();
        this.paymentOrderPatterns = new LinkedHashSet<>();
    }

    /*
    ########### MAPPINGS #####################
     */

    /**
     * OneToMany association between client and bank accounts.
     *
     * @return
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "owner_id")
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
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "name")
    public Set<PatternPaymentOrder> getPaymentOrderPatterns() {
        return paymentOrderPatterns;
    }

    public void setPaymentOrderPatterns(Set<PatternPaymentOrder> paymentOrderPatterns) {
        this.paymentOrderPatterns = paymentOrderPatterns;
    }
}
