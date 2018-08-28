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
    public void one_month_0801_0831() {

        when(repo.getAll()).thenReturn(Arrays.asList(
                new Budget("201808", 310)
        ));
        LocalDate startDate = LocalDate.of(2018, 8, 1);
        LocalDate endDate = LocalDate.of(2018, 8, 31);


        Assert.assertEquals(310.0, budgetService.queryBudget(startDate, endDate), 0.001);
    }

}