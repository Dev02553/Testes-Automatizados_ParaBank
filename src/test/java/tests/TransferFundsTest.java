package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferFundsTest extends BaseTest {

    @Test
    public void testTransferenciaFundos() {
        ensureAtLeastTwoAccounts();
        goTo("Transfer Funds");

        By from = By.id("fromAccountId");
        By to = By.id("toAccountId");
        waitUntilSelectHasOptions(from);
        waitUntilSelectHasOptions(to);

        Select fromSelect = new Select(driver.findElement(from));
        Select toSelect = new Select(driver.findElement(to));

        // garante contas diferentes (quando possível)
        fromSelect.selectByIndex(0);
        if (toSelect.getOptions().size() > 1) toSelect.selectByIndex(1);
        else toSelect.selectByIndex(0);

        type(By.id("amount"), "10");
        click(By.cssSelector("input.button"));

        waitForRightPanelReady();
        String page = driver.getPageSource().toLowerCase();

        assertTrue(
                page.contains("transfer complete") ||
                page.contains("was successfully transferred") ||
                page.contains("success"),
                "Transferência não confirmou sucesso."
        );
    }
}
