package org.danekja.edu.pia.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * Date: 29.11.16
 *
 * @author Jakub Danek
 */
@Service
@PreAuthorize("authenticated")
public class ConfigurableSecretManager implements SecretManager {

    @Value("${secret.value}")
    private String secret;

    @Override
    public String getSecret() {
        return secret;
    }
}
