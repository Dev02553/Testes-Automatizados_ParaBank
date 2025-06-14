package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

public class OpenAccountTest extends BaseTest {

    @Test
    public void testAbrirNovaConta() {
        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.cssSelector("input[type='submit']")).click();
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        driver.findElement(By.linkText("Open New Account")).click();
        new Select(driver.findElement(By.id("type"))).selectByVisibleText("SAVINGS");
        driver.findElement(By.cssSelector("input.button")).click();
         
    }
}