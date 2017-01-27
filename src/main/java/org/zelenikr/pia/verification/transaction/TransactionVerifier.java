package org.zelenikr.pia.verification.transaction;

import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.verification.Verifier;

/**
 * {@link Verifier} interface specification for transactions.
 *
 * @author Roman Zelenik
 */
public interface TransactionVerifier extends Verifier<PaymentTransaction> {
}
