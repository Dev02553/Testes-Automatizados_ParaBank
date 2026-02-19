package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenAccountTest extends BaseTest {

    @Test
    public void testAbrirNovaConta() {
        loginDefault();
        goTo("Open New Account");

        waitForVisible(By.id("type"));
        new Select(driver.findElement(By.id("type"))).selectByVisibleText("SAVINGS");
        click(By.cssSelector("input.button"));

        // pode aparecer pelo id ou por texto
        wait.until(d ->
                isPresent(By.id("newAccountId")) ||
                d.getPageSource().toLowerCase().contains("account opened") ||
                d.getPageSource().toLowerCase().contains("congratulations")
        );

        assertTrue(
                isPresent(By.id("newAccountId")) || driver.getPageSource().toLowerCase().contains("account opened"),
                "Não confirmou a abertura de nova conta."
        );
    }
}
