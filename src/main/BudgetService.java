import java.time.LocalDate;
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
        while(refStartDate.isBefore(end)) {

            // get the budget of this month
            LocalDate startOfMonth = LocalDate.of(refStartDate.getYear(), refStartDate.getMonth(), 1);
            int amount = budgetMap.get(startOfMonth);

            LocalDate refEndDate;
            LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1);
            if (end.isAfter(endOfMonth)){
                refEndDate = endOfMonth;
            } else {
                refEndDate = end;
            }

            // calculate month
            int days = refEndDate.getDayOfMonth() - refStartDate.getDayOfMonth() + 1;

            double calculatedAmount = days == refStartDate.lengthOfMonth()
                                      ? amount
                                      : amount /refStartDate.lengthOfMonth() * days;

            result += calculatedAmount;

            refStartDate = refStartDate.plusMonths(1);
        }

        result = Math.round(result * 100.0) / 100.0;

        return result;
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
