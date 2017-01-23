package org.zelenikr.pia.validation.configurable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.PatternPaymentOrder;
import org.zelenikr.pia.validation.PatternPaymentOrderValidator;
import org.zelenikr.pia.validation.exception.PatternPaymentOrderValidationException;

/**
 * @author Roman Zelenik
 */
@Service
public class ConfigurablePatternPaymentOrderValidator implements PatternPaymentOrderValidator {

    @Value("${validation.patternPaymentOrder.name.maxLength}")
    private int nameMaxLength;

    @Value("${validation.patternPaymentOrder.name.minLength}")
    private int nameMinLength;

    @Override
    public int getNameMaxLength() {
        return nameMaxLength;
    }

    @Override
    public int getNameMinLength() {
        return nameMinLength;
    }

    @Override
    public void validate(PatternPaymentOrder pattern) throws PatternPaymentOrderValidationException {
        validateName(pattern.getName());
    }

    private void validateName(String name) throws PatternPaymentOrderValidationException {
        if (StringUtils.isBlank(name)) throw new PatternPaymentOrderValidationException("Name is a required field");
        if (name.length() < getNameMinLength() || name.length() > getNameMaxLength())
            throw new PatternPaymentOrderValidationException("Length of pattern must be in the interval <"
                    + getNameMinLength() + ";" + getNameMaxLength() + ">");
        if (!StringUtils.isAlphanumericSpace(name))
            throw new PatternPaymentOrderValidationException("Name can contain only alpha numeric characters and space");
    }

}
