package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountOverviewTest extends BaseTest {

    @Test
    public void testVisualizarResumoConta() {
        loginDefault();
        goTo("Accounts Overview");

        // Valida presença da tabela ou do texto
        String page = driver.getPageSource().toLowerCase();
        assertTrue(
                isPresent(By.id("accountTable")) || page.contains("accounts overview"),
                "Não apareceu o resumo de contas (Accounts Overview)."
        );
    }
}
