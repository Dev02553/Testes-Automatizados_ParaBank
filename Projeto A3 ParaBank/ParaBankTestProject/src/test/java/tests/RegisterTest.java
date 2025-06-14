package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class RegisterTest extends BaseTest {

    @Test
    public void testRegistroUsuario() {
        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("customer.firstName")).sendKeys("Novo");
        driver.findElement(By.id("customer.lastName")).sendKeys("Usuario");
        driver.findElement(By.id("customer.address.street")).sendKeys("Rua Teste 123");
        driver.findElement(By.id("customer.address.city")).sendKeys("Cidade");
        driver.findElement(By.id("customer.address.state")).sendKeys("Estado");
        driver.findElement(By.id("customer.address.zipCode")).sendKeys("00000");
        driver.findElement(By.id("customer.phoneNumber")).sendKeys("11999999999");
        driver.findElement(By.id("customer.ssn")).sendKeys("123-45-6789");
        driver.findElement(By.id("customer.username")).sendKeys("novoteste123");
        driver.findElement(By.id("customer.password")).sendKeys("123456");
        driver.findElement(By.id("repeatedPassword")).sendKeys("123456");
        driver.findElement(By.cssSelector("input.button")).click();
    }
}