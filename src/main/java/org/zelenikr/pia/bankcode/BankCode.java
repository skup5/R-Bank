package org.zelenikr.pia.bankcode;

/**
 * Instance of this class represents bank code with name of bank, which code belongs.
 *
 * @author Roman Zelenik
 */
public class BankCode {

    private String code;
    private String bankName;

    public BankCode(String code) {
        this(code, "");
    }

    public BankCode(String code, String bankName) {
        this.code = code;
        this.bankName = bankName;
    }

    public String getCode() {
        return code;
    }

    public String getBankName() {
        return bankName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankCode)) return false;

        BankCode bankCode = (BankCode) o;

        return code != null ? code.equals(bankCode.code) : bankCode.code == null;
    }

    @Override
    public int hashCode() {
        return code != null ? code.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BankCode{");
        sb.append("code='").append(code).append('\'');
        sb.append(", bankName='").append(bankName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
