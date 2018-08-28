package budget;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class BudgetServiceTest {

    IRepo repo = mock(IRepo.class);
    BudgetService budgetService = new BudgetService(repo);

    @Test
    public void budget_one_month_0801_0831() {

        givenBudgetList("201808", 310);
        LocalDate startDate = LocalDate.of(2018, 8, 1);
        LocalDate endDate = LocalDate.of(2018, 8, 31);

        budgetShouldBe(startDate, endDate, 310.0);
    }

    @Test
    public void budget_one_month_0810_0820() {

        givenBudgetList("201808", 310);
        LocalDate startDate = LocalDate.of(2018, 8, 10);
        LocalDate endDate = LocalDate.of(2018, 8, 20);

        budgetShouldBe(startDate, endDate, 110.0);
    }

    @Test
    public void budget_one_month_0810_0901() {

        givenBudgetList("201808", 310);
        LocalDate startDate = LocalDate.of(2018, 8, 10);
        LocalDate endDate = LocalDate.of(2018, 9, 1);

        budgetShouldBe(startDate, endDate, 220);
    }

    @Test
    public void budget_two_month_0831_0902() {

        givenBudgetList(new Budget("201808",  310), new Budget("201809", 300) );
        LocalDate startDate = LocalDate.of(2018, 8, 31);
        LocalDate endDate = LocalDate.of(2018, 9, 1);

        budgetShouldBe(startDate, endDate, 20);
    }

    @Test
    public void budget_two_month_diff_money_0831_0902() {

        givenBudgetList(new Budget("201808",  310), new Budget("201809", 150) );
        LocalDate startDate = LocalDate.of(2018, 8, 31);
        LocalDate endDate = LocalDate.of(2018, 9, 1);

        budgetShouldBe(startDate, endDate, 15);
    }

    @Test
    public void budget_diff_year_0831_0902() {

        givenBudgetList(new Budget("201708",  310),
                        new Budget("201809", 150) );
        LocalDate startDate = LocalDate.of(2017, 8, 1);
        LocalDate endDate = LocalDate.of(2018, 10, 1);

        budgetShouldBe(startDate, endDate, 460);
    }

    @Test
    public void budget_one_month_0229() {

        givenBudgetList(new Budget("201602",  290));
        LocalDate startDate = LocalDate.of(2016, 2, 29);
        LocalDate endDate = LocalDate.of(2016, 2, 29);

        budgetShouldBe(startDate, endDate, 10);
    }

    @Test
    public void budget_one_month_0801_0831_double_points() {

        givenBudgetList(new Budget("201808",  290));
        LocalDate startDate = LocalDate.of(2018, 8, 1);
        LocalDate endDate = LocalDate.of(2018, 8, 29);

        budgetShouldBe(startDate, endDate, 271.29);
    }

    private void givenBudgetList(Budget... budgets) {
        when(repo.getAll()).thenReturn(Arrays.asList(
                budgets
        ));
    }

    private void givenBudgetList(String yearMonth, int amount) {
        when(repo.getAll()).thenReturn(Arrays.asList(
                new Budget(yearMonth, amount)
        ));
    }

    private void budgetShouldBe(LocalDate startDate, LocalDate endDate, double expected) {
        Assert.assertEquals(expected, budgetService.queryBudget(startDate, endDate), 0.001);
    }
}