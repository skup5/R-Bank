package org.zelenikr.pia.manager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Roman Zelenik
 */
@Service
public class ConfigurableDBSettingsManager implements DBSettingsManager {

    @Value("${db.connection.url}")
    private String url;

    @Value("${db.connection.username}")
    private String username;

    @Value("${db.connection.password}")
    private String password;

    @Value("${db.connection.driverClassName}")
    private String driver;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getDriver() {
        return driver;
    }
}
