package budget;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetService {
    private IRepo repo;

    public BudgetService(IRepo repo) {
        this.repo = repo;
    }

    public Double queryBudget(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            return 0.0;
        }

        Double result = 0.0;
        Map<LocalDate, Integer> budgetMap = convertAll();
        LocalDate refStartDate = LocalDate.of(start.getYear(), start.getMonth(), start.getDayOfMonth());
        while(isInQueryPeriod(refStartDate, end)) {
            // get the budget of this month
            LocalDate startOfMonth = getStartDateOfMonth(refStartDate.getYear(), refStartDate.getMonth());
            int amount = getMonthAmount(budgetMap, startOfMonth);

            LocalDate refEndDate = getRefEndDate(end, startOfMonth);
            result += calculatedAmount(refStartDate, refEndDate, amount);

            refStartDate = startOfMonth.plusMonths(1);
        }

        result = Math.round(result * 100.0) / 100.0;

        return result;
    }

    private LocalDate getRefEndDate(LocalDate end, LocalDate startOfMonth) {
        LocalDate endOfMonth = getEndOfMonth(startOfMonth);
        if (end.isAfter(endOfMonth)){
            return endOfMonth;
        } else {
            return end;
        }
    }

    private double calculatedAmount(LocalDate refStartDate, LocalDate refEndDate, int amount) {
        int days = getDays(refStartDate, refEndDate);
        return days == refStartDate.lengthOfMonth()
                                              ? amount
                                              : (double) amount /refStartDate.lengthOfMonth() * days;
    }

    private int getDays(LocalDate refStartDate, LocalDate refEndDate) {
        return refEndDate.getDayOfMonth() - refStartDate.getDayOfMonth() + 1;
    }

    private LocalDate getEndOfMonth(LocalDate startOfMonth) {
        return startOfMonth.plusMonths(1).minusDays(1);
    }

    private int getMonthAmount(Map<LocalDate, Integer> budgetMap, LocalDate startOfMonth) {
        return budgetMap.containsKey(startOfMonth) ? budgetMap.get(startOfMonth) : 0;
    }

    private boolean isInQueryPeriod(LocalDate refStartDate, LocalDate end) {
        return refStartDate.isBefore(end) || refStartDate.isEqual(end);
    }

    private LocalDate getStartDateOfMonth(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    private Map<LocalDate, Integer> convertAll() {
        List<Budget> budgetList = repo.getAll();

        HashMap<LocalDate, Integer> result = new HashMap<LocalDate, Integer>();
        for(Budget budget : budgetList) {
            result.put(budget.convertYearMonth(), budget.amount);
        }
        return result;

    }

}
