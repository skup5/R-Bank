package org.zelenikr.pia.domain;

import javax.persistence.Embeddable;

/**
 * Represents offset bank account in all {@link AbstractPaymentOrder} instances.
 *
 * @author Roman Zelenik
 */
@Embeddable
public class OffsetAccount {

    private String offsetAccountNumber;
    private String bankCode;

    public OffsetAccount() {
    }

    public OffsetAccount(String offsetAccountNumber, String bankCode) {
        this.offsetAccountNumber = offsetAccountNumber;
        this.bankCode = bankCode;
    }


    /*
    ########### MAPPINGS #####################
     */

    public String getOffsetAccountNumber() {
        return offsetAccountNumber;
    }

    public void setOffsetAccountNumber(String offsetAccountNumber) {
        this.offsetAccountNumber = offsetAccountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OffsetAccount)) return false;

        OffsetAccount that = (OffsetAccount) o;

        if (offsetAccountNumber != null ? !offsetAccountNumber.equals(that.offsetAccountNumber) : that.offsetAccountNumber != null) return false;
        return bankCode != null ? bankCode.equals(that.bankCode) : that.bankCode == null;
    }

    @Override
    public int hashCode() {
        int result = offsetAccountNumber != null ? offsetAccountNumber.hashCode() : 0;
        result = 31 * result + (bankCode != null ? bankCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OffsetAccount{");
        sb.append("number=").append(offsetAccountNumber);
        sb.append(", bankCode=").append(bankCode);
        sb.append('}');
        return sb.toString();
    }
}
