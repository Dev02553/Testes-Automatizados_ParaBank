package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginValido() {
        loginDefault();
        String page = driver.getPageSource().toLowerCase();
        assertTrue(page.contains("accounts overview") || isLoggedIn(), "Login válido não confirmou acesso.");
    }

    @Test
    public void testLoginInvalido() {
        open("/index.htm");
        type(org.openqa.selenium.By.name("username"), "usuarioInvalido_" + System.currentTimeMillis());
        type(org.openqa.selenium.By.name("password"), "senhaErrada");
        click(org.openqa.selenium.By.cssSelector("#loginPanel input[type='submit']"));

        // espera o rightPanel e valida mensagem
        waitForRightPanelReady();
        String page = driver.getPageSource().toLowerCase();

        assertTrue(
                page.contains("could not be verified") ||
                page.contains("error") ||
                page.contains("invalid"),
                "Mensagem de erro não encontrada no login inválido."
        );
    }
}
