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