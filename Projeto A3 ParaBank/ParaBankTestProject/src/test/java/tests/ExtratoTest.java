package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.junit.jupiter.api.Assertions;

public class ExtratoTest extends BaseTest {

    @Test
    public void testVerExtrato() throws InterruptedException {
        // Login
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        Thread.sleep(800);

        Assertions.assertTrue(driver.getPageSource().contains("Accounts Overview"));

        driver.findElement(By.linkText("Accounts Overview")).click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//table[@id='accountTable']//a")).click();
        Thread.sleep(1000);

        Assertions.assertTrue(driver.getPageSource().contains("Transaction Details") 
            || driver.getCurrentUrl().contains("activity.htm"), "Página de extrato não foi carregada corretamente.");
    }
}
