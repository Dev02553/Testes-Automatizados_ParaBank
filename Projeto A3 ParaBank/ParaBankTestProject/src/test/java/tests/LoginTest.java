package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.junit.jupiter.api.Assertions;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginValido() throws InterruptedException {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        Thread.sleep(1000); 
        Assertions.assertTrue(driver.getPageSource().contains("Accounts Overview"));
    }

    public void testLoginInvalido() throws InterruptedException {
        driver.findElement(By.name("username")).sendKeys("usuarioInvalido");
        driver.findElement(By.name("password")).sendKeys("senhaErrada");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        Thread.sleep(1000);

        String pageSource = driver.getPageSource();
        Assertions.assertTrue(
            pageSource.contains("The username and password could not be verified.") ||
            pageSource.toLowerCase().contains("error"),
            "Mensagem de erro não encontrada."
        );
    }
}