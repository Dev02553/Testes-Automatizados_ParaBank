package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class UpdateContactInfoTest extends BaseTest {

    @Test
    public void testAtualizarContato() {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        driver.findElement(By.linkText("Update Contact Info")).click();
        driver.findElement(By.id("customer.address.street")).clear();
        driver.findElement(By.id("customer.address.street")).sendKeys("Rua Atualizada 456");
        driver.findElement(By.cssSelector("input[value='Update Profile']")).click();
    }
}