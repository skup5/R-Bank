package org.zelenikr.pia.verification.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.utils.EmailSender;
import org.zelenikr.pia.verification.MessageRecipient;

/**
 * @author Roman Zelenik
 */
@Service
public class DefualtAuthenticationCodeSender implements AuthenticationVerificationCodeSender {

    private EmailSender emailSender;

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public boolean send(String code, MessageRecipient recipient) {
        String message = createCSMessage(code);
        System.out.println("DefualtAuthenticationCodeSender.send");
        System.out.println(message);
//        return true;
        return emailSender.send(recipient.getEmailAddress(), "Login verification", message);
    }

    private String createCSMessage(String code) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Potvrzovaci kod: ").append(code)
                .append(" pro prihlaseni. R-Bank");
        return messageBuilder.toString();
    }
}
