package org.zelenikr.pia.utils;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * @author Roman Zelenik
 */
public class DefaultEmailSender implements EmailSender {

    private MailSender mailSender;

    public DefaultEmailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public boolean send(String to, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        try {
            mailSender.send(mailMessage);
            return true;
        } catch (MailException e) {
            e.printStackTrace();
            return false;
        }
    }

}
