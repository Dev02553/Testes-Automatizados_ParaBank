package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterTest extends BaseTest {

    @Test
    public void testRegistroUsuario() {

        // Abre direto a página de registro
        open("/register.htm");

        // Espera o formulário carregar
        waitForVisible(By.id("customer.firstName"));

        // Tenta até 3 vezes
        for (int attempt = 1; attempt <= 3; attempt++) {

            String user = "user" + System.currentTimeMillis() + "_" + attempt;

            type(By.id("customer.firstName"), "Novo");
            type(By.id("customer.lastName"), "Usuario");
            type(By.id("customer.address.street"), "Rua Teste 123");
            type(By.id("customer.address.city"), "Cidade");
            type(By.id("customer.address.state"), "Estado");
            type(By.id("customer.address.zipCode"), "00000");
            type(By.id("customer.phoneNumber"), "11999999999");
            type(By.id("customer.ssn"), "123-45-6789");
            type(By.id("customer.username"), user);
            type(By.id("customer.password"), "123456");
            type(By.id("repeatedPassword"), "123456");

            // ✅ CLICA O BOTÃO DO FORM DE REGISTRO (e NÃO o login)
            WebElement registerForm =
                    driver.findElement(By.id("customer.firstName"))
                          .findElement(By.xpath("./ancestor::form"));

            // tenta pegar um submit específico do form
            WebElement submit = registerForm.findElement(By.cssSelector("input[type='submit'], input.button"));
            submit.click();

            // Espera o painel responder
            waitForRightPanelReady();

            String page = driver.getPageSource().toLowerCase();
            boolean logged = isPresent(By.linkText("Log Out")) ||
                    isPresent(By.cssSelector("#leftPanel a[href*='logout.htm']"));

            boolean successText =
                    page.contains("welcome") ||
                    page.contains("your account was created") ||
                    page.contains("has been created");

            if (logged || successText) {
                assertTrue(true);
                return;
            }

            // username já existe -> tenta outro
            if (page.contains("already exists")) {
                open("/register.htm");
                waitForVisible(By.id("customer.firstName"));
                continue;
            }

            // instabilidade real
            if (page.contains("system is offline") || page.contains("temporarily unavailable")) {
                Assumptions.assumeTrue(false,
                        "ParaBank demo instável/offline durante o registro. Teste pulado para não quebrar a suíte.");
            }

            // Se caiu no erro do LOGIN vazio, é sinal que clicou o botão errado (agora não deveria)
            if (page.contains("please enter a username and password")) {
                open("/register.htm");
                waitForVisible(By.id("customer.firstName"));
                continue;
            }

            // Falha real: mostra painel para debug
            String rightPanel = "";
            try { rightPanel = driver.findElement(By.id("rightPanel")).getText(); } catch (Exception ignored) {}
            throw new AssertionError("Registro falhou. Conteúdo do painel:\n" + rightPanel);
        }

        throw new AssertionError("Falha ao registrar após 3 tentativas.");
    }
}