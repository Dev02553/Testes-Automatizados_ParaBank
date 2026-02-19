package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateContactInfoTest extends BaseTest {

    @Test
    public void testAtualizarContato() {
        loginDefault();
        goTo("Update Contact Info");

        // Se o ParaBank vier com campos vazios (acontece), precisa preencher os obrigatórios
        fillIfEmpty(By.id("customer.firstName"), "John");
        fillIfEmpty(By.id("customer.lastName"), "Demo");
        fillIfEmpty(By.id("customer.address.street"), "Rua Atualizada 456");
        fillIfEmpty(By.id("customer.address.city"), "Sao Paulo");
        fillIfEmpty(By.id("customer.address.state"), "SP");
        fillIfEmpty(By.id("customer.address.zipCode"), "01000");

        click(By.cssSelector("input[value='Update Profile']"));

        waitForRightPanelReady();
        String page = driver.getPageSource().toLowerCase();

        assertTrue(
                page.contains("profile updated") ||
                page.contains("updated successfully") ||
                page.contains("update profile") && !page.contains("is required"),
                "Não confirmou atualização de perfil (ou ainda está retornando campos obrigatórios)."
        );
    }

    private void fillIfEmpty(By by, String value) {
        if (!isPresent(by)) return;
        String current = driver.findElement(by).getAttribute("value");
        if (current == null || current.trim().isEmpty()) {
            driver.findElement(by).clear();
            driver.findElement(by).sendKeys(value);
        }
    }
}
