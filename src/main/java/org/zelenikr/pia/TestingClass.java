package org.zelenikr.pia;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.FileTemplateLoader;
import org.zelenikr.pia.bankcode.BankCode;
import org.zelenikr.pia.utils.*;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Roman Zelenik
 */
public class TestingClass {

    public static void main(String[] args) {
//        csv("https://www.cnb.cz/cs/platebni_styk/ucty_kody_bank/download/kody_bank_CR.csv");

//        printHash("0001");
//        printHash("0002");
    }

    static void printHash(String s) {
        System.out.println(new PasswordHashEncoder().encode(s));

    }

    static void jade() {
        JadeConfiguration config = new JadeConfiguration();
        String basePath = new File("src/main/webapp/WEB-INF/jade/").getAbsolutePath();
        config.setTemplateLoader(new FileTemplateLoader(basePath, "UTF-8"));
//            JadeTemplate template = config.getTemplate("/index.jade");
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("company", "neuland");

//            System.out.println(basePath);

//            System.out.println(config.renderTemplate(template, model));

    }

    static void csv(String file) {
        List<BankCode> codes = new ArrayList<>();
        CSVLoader loader = new WebCSVLoader();
        String[] row;
        try (CSVFileReader reader = loader.getReader(file)) {
            // headers
            reader.nextRow();
            // data rows
            while (reader.hasNextRow()) {
                row = reader.nextRow();
                codes.add(new BankCode(row[0], row[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (BankCode code : codes) {
            System.out.println(code);
        }
    }
}
