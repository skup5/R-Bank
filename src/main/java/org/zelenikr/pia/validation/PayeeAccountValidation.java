package org.zelenikr.pia.validation;

/**
 * @author Roman Zelenik
 */
public interface PayeeAccountValidation {
    int getNumberMaxLength();

    int getNumberMinLength();

    int getBankCodeLength();
}
