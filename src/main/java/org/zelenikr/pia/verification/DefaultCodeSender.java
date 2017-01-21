package org.zelenikr.pia.verification;

import org.springframework.stereotype.Service;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.domain.PaymentTransaction;

/**
 * @author Roman Zelenik
 */
@Service
public class DefaultCodeSender implements VerificationCodeSender {


    @Override
    public boolean send(String code, Client recipient, PaymentTransaction transaction) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("Client: ").append(recipient.displayName())
                .append(" Transaction: ").append(transaction.getId())
                .append(" VerifyCode: ").append(code);

        System.out.println("DefaultCodeSender.send");
        System.out.println(messageBuilder.toString());
        return true;
    }
}
