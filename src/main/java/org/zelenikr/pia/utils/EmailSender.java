package org.zelenikr.pia.utils;

/**
 * @author Roman Zelenik
 */
public interface EmailSender {

    /**
     *
     * @param to email address of recipient
     * @param subject mail subject
     * @param message mail content
     * @return true if mail was successfully sent
     */
    boolean send(String to, String subject, String message);
}
