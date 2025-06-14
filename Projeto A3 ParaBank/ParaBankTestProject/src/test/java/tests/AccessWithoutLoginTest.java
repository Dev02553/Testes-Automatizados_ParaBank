package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;

public class AccessWithoutLoginTest extends BaseTest {

    @Test
    public void testAcessoSemLogin() {
        driver.get("https://parabank.parasoft.com/parabank/openaccount.htm");
    }
}