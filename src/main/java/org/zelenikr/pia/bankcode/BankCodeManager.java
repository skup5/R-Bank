package org.zelenikr.pia.bankcode;

/**
 * @author Roman Zelenik
 */
public interface BankCodeManager {

    /**
     * Returns {@link BankCode} object for this bank.
     *
     * @return bank code this bank
     */
    BankCode getBankCode();

    /**
     * Returns list of available {@link BankCode} instances, includes {@link BankCode} this bank.
     *
     * @return array of bank codes or empty array
     */
    BankCode[] getBankCodes();

    String getBankName(String bankCode);

    /**
     * Checks if the specific bankCode is bank code this bank.
     *
     * @param bankCode checked bank code
     * @return true if bankCode is equal to bank code this bank
     */
    boolean isOurBank(String bankCode);
}
