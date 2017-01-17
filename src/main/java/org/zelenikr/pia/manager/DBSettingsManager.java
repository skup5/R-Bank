package org.zelenikr.pia.manager;

/**
 * Manages database connection properties.
 *
 * @author Roman Zelenik
 */
public interface DBSettingsManager {

    String getUrl();
    String getUsername();
    String getPassword();
    String getDriver();
}
