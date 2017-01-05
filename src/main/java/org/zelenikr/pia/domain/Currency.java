package org.zelenikr.pia.domain;

import javax.persistence.Embeddable;

/**
 * @author Roman Zelenik
 */
@Embeddable
public enum Currency {
    CZK,
    EUR,
    USD
}
