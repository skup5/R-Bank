package org.zelenikr.pia.verification.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.PaymentTransaction;
import org.zelenikr.pia.utils.EmailSender;
import org.zelenikr.pia.verification.MessageRecipient;

/**
 * @author Roman Zelenik
 */
@Service
public class DefaultTransactionCodeSender implements TransactionVerificationCodeSender {

    private EmailSender emailSender;

    @Autowired
    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public boolean send(String code, MessageRecipient recipient, PaymentTransaction transaction) {
        String message = createCSMessage(code, transaction);
        System.out.println("DefaultCodeSenderTransaction.send");
        System.out.println(message);
//        return true;
        return emailSender.send(recipient.getEmailAddress(), "Payment verification", message);
    }

    private String createCSMessage(String code, PaymentTransaction transaction) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Potvrzovaci kod: ").append(code)
                .append(" pro platbu cislo: ").append(transaction.getId())
                .append(" na castku: ").append(transaction.getAmount())
                .append(transaction.getCurrency().name())
                .append(". R-Bank");
        return messageBuilder.toString();
    }
}
