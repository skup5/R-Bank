package org.zelenikr.pia.bankcode;

/**
 * @author Roman Zelenik
 */
public interface BankCodeManager {
    BankCode getBankCode();

    BankCode[] getBankCodes();

    String getBankName(String bankCode);
}
