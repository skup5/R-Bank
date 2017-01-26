package org.zelenikr.pia.verification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.utils.EmailSender;

/**
 * @author Roman Zelenik
 */
@Service
public class DefaultCodeSender implements VerificationCodeSender {

    private EmailSender emailSender;

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public boolean send(String code, Client recipient, PaymentTransaction transaction) {
        String message = createCSMessage(code, transaction);
        System.out.println("DefaultCodeSender.send");
        System.out.println(message);
        emailSender.send(recipient.getEmail(), "Payment verification", message);
        return true;
    }

    private String createCSMessage(String code, PaymentTransaction transaction) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Potvrzovaci kod: ").append(code)
                .append(" pro platbu cislo: ").append(transaction.getId())
                .append(" na castku: ").append(transaction.getAmount())
                .append(transaction.getCurrency().name());
        return messageBuilder.toString();
    }
}
