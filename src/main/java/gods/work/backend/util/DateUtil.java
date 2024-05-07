package gods.work.backend.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String getCurrentDateAsString() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.format(FORMATTER);
    }
}
