package org.zelenikr.pia.domain;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Payment order entity pattern.
 *
 * @author Roman Zelenik
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractPaymentOrder extends BaseObject {

    protected Date dueDate;
    protected BankAccount clientAccount;
    protected PayeeAccount payeeAccount;
    protected BigDecimal amount;
    protected Integer constSymbol;
    protected String variableSymbol;
    protected String specificSymbol;
    protected String message;

    public AbstractPaymentOrder() {
    }

    public AbstractPaymentOrder(Date dueDate, BigDecimal amount, Integer constSymbol, String variableSymbol, String specificSymbol, String message) {
        this.dueDate = dueDate;
        this.amount = amount;
        this.constSymbol = constSymbol;
        this.variableSymbol = variableSymbol;
        this.specificSymbol = specificSymbol;
        this.message = message;
    }

    /*
    ########### MAPPINGS #####################
     */

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @ManyToOne
    public BankAccount getClientAccount() {
        return clientAccount;
    }

    public void setClientAccount(BankAccount clientAccount) {
        this.clientAccount = clientAccount;
    }

    @Embedded
    public PayeeAccount getPayeeAccount() {
        return payeeAccount;
    }

    public void setPayeeAccount(PayeeAccount payeeAccount) {
        this.payeeAccount = payeeAccount;
    }

    @Column(scale = 2)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getConstSymbol() {
        return constSymbol;
    }

    public void setConstSymbol(Integer constSymbol) {
        this.constSymbol = constSymbol;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractPaymentOrder)) return false;

        AbstractPaymentOrder that = (AbstractPaymentOrder) o;

        if (dueDate != null ? !dueDate.equals(that.dueDate) : that.dueDate != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (constSymbol != null ? !constSymbol.equals(that.constSymbol) : that.constSymbol != null) return false;
        if (variableSymbol != null ? !variableSymbol.equals(that.variableSymbol) : that.variableSymbol != null)
            return false;
        return specificSymbol != null ? specificSymbol.equals(that.specificSymbol) : that.specificSymbol == null;
    }

    @Override
    public int hashCode() {
        int result = dueDate != null ? dueDate.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (constSymbol != null ? constSymbol.hashCode() : 0);
        result = 31 * result + (variableSymbol != null ? variableSymbol.hashCode() : 0);
        result = 31 * result + (specificSymbol != null ? specificSymbol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('{');
        sb.append("dueDate=").append(dueDate);
        sb.append(", amount=").append(amount);
        sb.append(", constSymbol=").append(constSymbol);
        sb.append(", variableSymbol=").append(variableSymbol);
        sb.append(", specificSymbol=").append(specificSymbol);
        sb.append('}');
        return sb.toString();
    }
}