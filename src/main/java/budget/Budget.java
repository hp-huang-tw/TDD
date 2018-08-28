package budget;

import java.time.LocalDate;

public class Budget {

    public String yearMonth;
    public int amount;

    public Budget(String yearMonth, int amount) {
        this.yearMonth = yearMonth;
        this.amount = amount;
    }

    public LocalDate convertYearMonth() {
        Integer year = Integer.parseInt(yearMonth.substring(0, 4));
        Integer month = Integer.parseInt(yearMonth.substring(4));
        return LocalDate.of(year, month, 1);
    }

}
