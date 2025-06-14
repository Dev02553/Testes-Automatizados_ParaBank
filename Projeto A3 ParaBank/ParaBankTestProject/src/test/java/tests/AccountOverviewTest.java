package tests;

import base.BaseTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

public class AccountOverviewTest extends BaseTest {

	@Test
	public void testVisualizarResumoConta() throws InterruptedException {
	    driver.findElement(By.name("username")).sendKeys("john");
	    driver.findElement(By.name("password")).sendKeys("demo");
	    driver.findElement(By.cssSelector("input[type='submit']")).click();
	    Thread.sleep(1000);

	    Assertions.assertTrue(driver.getPageSource().contains("Accounts Overview"));
	    driver.findElement(By.linkText("Accounts Overview")).click();
	}

}