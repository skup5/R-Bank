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
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }

        public String toString(Date date) {
            return date == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(date);
        }
    }
}
