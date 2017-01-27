package org.zelenikr.pia.verification.user;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.User;
import org.zelenikr.pia.verification.VerificationSettings;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of {@link UserVerifier} interface.
 *
 * @author Roman Zelenik
 */
@Service
public class AlphaNumericUserVerifier implements UserVerifier {

    private VerificationSettings settings;
    private Map<Long, String> codes;

    @Autowired
    public AlphaNumericUserVerifier(VerificationSettings settings) {
        this.settings = settings;
        this.codes = new HashMap<>();
    }

    @Override
    public String generateCode(User user) {
        String newCode = RandomStringUtils.randomAlphanumeric(settings.getTransactionCodeLength());
        codes.put(user.getId(), newCode);
        return newCode;
    }

    @Override
    public boolean verifyObject(User user, String code) {
        if (codes.containsKey(user.getId()) && codes.get(user.getId()).equals(code)) {
            codes.remove(user.getId());
            return true;
        } else
            return false;
    }

    @Override
    public void forgetObject(User user) {
        codes.remove(user.getId());
    }
}
