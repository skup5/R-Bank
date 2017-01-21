package org.zelenikr.pia.utils;

/**
 * @author Roman Zelenik
 */
public interface EmailSender {

    void send(String from, String to, String subject, String message);
}
