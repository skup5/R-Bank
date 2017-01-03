package org.zelenikr.pia;

import org.zelenikr.pia.utils.PasswordHash;
import org.zelenikr.pia.utils.PasswordHashEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Roman Zelenik
 */
public class TestingClass {

    public static void main(String[] args) {
      /*  try {
            JadeConfiguration config = new JadeConfiguration();
            String basePath = new File("src/main/webapp/WEB-INF/jade/").getAbsolutePath();
            config.setTemplateLoader(new FileTemplateLoader(basePath, "UTF-8"));
//            JadeTemplate template = config.getTemplate("/index.jade");
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("company", "neuland");

//            System.out.println(basePath);

//            System.out.println(config.renderTemplate(template, model));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }*/

      printHash("1234");
    }

    static void printHash(String s) {
        System.out.println(new PasswordHashEncoder().encode(s));

    }
}
