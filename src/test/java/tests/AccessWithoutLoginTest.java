package tests;

import base.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccessWithoutLoginTest extends BaseTest {

    @Test
    public void testAcessoSemLogin() {
        open("/overview.htm");

        String page = driver.getPageSource().toLowerCase();
        assertTrue(
                page.contains("customer login") ||
                page.contains("please enter a username") ||
                page.contains("error"),
                "Era esperado bloqueio/redirecionamento ao tentar acessar sem login."
        );
    }
}
