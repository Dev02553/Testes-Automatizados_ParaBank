package tests;

import base.BaseTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class TransferenciaSaldoInsuficienteTest extends BaseTest {

	@Test
    public void testTransferenciaSemSaldo() throws InterruptedException {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        Thread.sleep(800);

        driver.findElement(By.linkText("Transfer Funds")).click();
        Thread.sleep(1000);

        Select fromSelect = new Select(driver.findElement(By.id("fromAccountId")));
        Select toSelect = new Select(driver.findElement(By.id("toAccountId")));

        Assertions.assertFalse(fromSelect.getOptions().isEmpty(), "Nenhuma conta disponível para origem");
        Assertions.assertFalse(toSelect.getOptions().isEmpty(), "Nenhuma conta disponível para destino");

        fromSelect.selectByIndex(0);
        toSelect.selectByIndex(0);

        driver.findElement(By.id("amount")).sendKeys("999999");
        driver.findElement(By.cssSelector("input.button")).click();
    }
}