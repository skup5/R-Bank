package org.zelenikr.pia.web.servlet.spring.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.zelenikr.pia.domain.BankAccount;
import org.zelenikr.pia.domain.Client;
import org.zelenikr.pia.manager.ClientGenerator;
import org.zelenikr.pia.validation.exception.ClientValidationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Roman Zelenik
 */
@WebServlet("/admin/account-list")
public class AccountListController extends AbstractAdminController {

    private static final String TEMPLATE_PATH = "admin/accountList";

    ClientGenerator clientGenerator;

    @Autowired
    public void setClientGenerator(ClientGenerator clientGenerator) {
        this.clientGenerator = clientGenerator;
    }

    @Override
    protected String getDefaultTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log("doGet()");
//        log("generating client...");
//        Client client = clientGenerator.newClientAccount();
//        log("client was generated");
//        for (Client c : clientManager.getClients()) {
//            try {
//                log("loading client details...");
//                c = clientManager.loadDetails(c);
//            } catch (ClientValidationException e) {
//                e.printStackTrace();
//            }
//            System.out.println(c);
//            System.out.println("--Bank accounts--");
//            for (BankAccount ba : c.getBankAccounts()) {
//                System.out.println(ba);
//            }
//            System.out.println("----------------------------");
//        }

//        clientManager.delete(client);
//        for (Client c : clientManager.getClients()) System.out.println("* " + c);


        dispatch(req, resp);
    }
}
