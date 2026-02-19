package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExtratoTest extends BaseTest {

    @Test
    public void testVerExtrato() {
        loginDefault();
        goTo("Accounts Overview");

        // Clica na primeira conta
        wait.until(d -> isPresent(By.cssSelector("#accountTable a")));
        click(By.cssSelector("#accountTable a"));

        // Confirma que entrou no activity.htm / extrato
        String page = driver.getPageSource().toLowerCase();
        assertTrue(
                page.contains("account details") ||
                page.contains("transaction") ||
                driver.getCurrentUrl().toLowerCase().contains("activity.htm"),
                "Página de extrato/atividade não foi carregada corretamente."
        );
    }
}
