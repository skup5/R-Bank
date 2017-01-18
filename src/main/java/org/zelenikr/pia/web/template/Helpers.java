package org.zelenikr.pia.web.template;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Roman Zelenik
 */
public class Helpers {
    private Helpers() {
    }

    public static class DateHelper {

        public String todayString() {
            return new SimpleDateFormat().format(new Date());
        }

        public String toLocaleDateString() {
            String format = "";
            return new SimpleDateFormat().format(new Date());
        }
//        public String localDate(String date){
//            new SimpleDateFormat().p
//        }
    }
}
