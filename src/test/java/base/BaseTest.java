package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BaseTest {

    // -------------------------------------------------------------------------
    // ThreadLocal: garante isolamento quando testes rodarem em paralelo no futuro
    // -------------------------------------------------------------------------
    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected final String baseUrl = resolveBaseUrl(
            System.getProperty("baseUrl", "https://parabank.parasoft.com/parabank")
    );

    protected final String defaultUser = propOrDefault("parabank.user", "john");
    protected final String defaultPass = propOrDefault("parabank.pass", "demo");

    // -------------------------------------------------------------------------
    // Watcher: captura screenshot tanto em falha quanto em sucesso (opcional)
    // e envia para o Allure como anexo visível no relatório
    // -------------------------------------------------------------------------
    @RegisterExtension
    TestWatcher watcher = new TestWatcher() {

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            // Screenshot vai para target/screenshots E para o relatório Allure
            captureScreenshotForAllure("FALHA — " + context.getDisplayName());
            try {
                saveScreenshotToDisk(context.getDisplayName());
            } catch (Exception ignored) {
                // best-effort: não interrompe o tearDown
            }
        }

        @Override
        public void testSuccessful(ExtensionContext context) {
            // Descomente a linha abaixo se quiser screenshot de testes que passam também:
            // captureScreenshotForAllure("OK — " + context.getDisplayName());
        }

        @Override
        public void testDisabled(ExtensionContext context, Optional<String> reason) {}

        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            captureScreenshotForAllure("ABORTADO — " + context.getDisplayName());
        }
    };

    // -------------------------------------------------------------------------
    // Setup
    // -------------------------------------------------------------------------

    @BeforeEach
    public void setUp() {
        // WebDriverManager resolve o chromedriver compatível com o Chrome instalado.
        // Não é mais necessário ter chromedriver.exe no repositório.
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        if (headless) {
            options.addArguments("--headless=new");
        }

        options.addArguments("--window-size=1280,800");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--lang=en-US");

        driver = new ChromeDriver(options);

        // Armazena no ThreadLocal para que o watcher acesse mesmo após o teste falhar
        driverHolder.set(driver);

        wait = new WebDriverWait(driver, Duration.ofSeconds(25));

        open("/index.htm");

        // Se o site estiver fora do ar, pula os testes em vez de quebrar o build
        String page = driver.getPageSource().toLowerCase();
        if (page.contains("system is offline") || page.contains("temporarily unavailable")) {
            Assumptions.assumeTrue(false, "ParaBank está offline/indisponível no momento.");
        }

        wait.until(d ->
                isPresent(By.name("username")) ||
                isPresent(By.id("loginPanel")) ||
                isPresent(By.id("leftPanel"))
        );
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        // Limpa o ThreadLocal para evitar leak em execuções paralelas
        driverHolder.remove();
    }

    // -------------------------------------------------------------------------
    // Acesso estático ao driver (usado pelo watcher e por utilitários externos)
    // -------------------------------------------------------------------------

    public static WebDriver getDriver() {
        return driverHolder.get();
    }

    // -------------------------------------------------------------------------
    // Allure: screenshot como anexo no relatório
    // -------------------------------------------------------------------------

    /**
     * Tira screenshot e anexa ao relatório Allure.
     * A anotação @Attachment instrui o Allure a capturar o retorno do método.
     */
    @Attachment(value = "{label}", type = "image/png")
    protected byte[] captureScreenshotForAllure(String label) {
        WebDriver d = driverHolder.get();
        if (d instanceof TakesScreenshot) {
            return ((TakesScreenshot) d).getScreenshotAs(OutputType.BYTES);
        }
        return new byte[0];
    }

    // -------------------------------------------------------------------------
    // Screenshot em disco (target/screenshots) — mantido para compatibilidade
    // -------------------------------------------------------------------------

    protected void saveScreenshotToDisk(String testName) throws Exception {
        if (driver == null) return;
        if (!(driver instanceof TakesScreenshot)) return;

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path outDir = Paths.get("target", "screenshots");
        Files.createDirectories(outDir);

        String safeName = testName.replaceAll("[^a-zA-Z0-9._-]", "_");
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path out = outDir.resolve(safeName + "_" + ts + ".png");

        Files.copy(src.toPath(), out);
        System.out.println("[BaseTest] Screenshot salvo: " + out);
    }

    // Mantém o nome antigo para não quebrar subclasses que já chamem saveScreenshot()
    protected void saveScreenshot(String testName) throws Exception {
        saveScreenshotToDisk(testName);
    }

    // -------------------------------------------------------------------------
    // URL helpers — sem alteração de comportamento
    // -------------------------------------------------------------------------

    protected void open(String pathOrUrl) {
        String v = (pathOrUrl == null) ? "" : pathOrUrl.trim();
        if (v.isEmpty()) v = "/index.htm";

        if (v.startsWith("http://") || v.startsWith("https://")) {
            System.out.println("[BaseTest] Abrindo: " + v);
            driver.get(v);
            return;
        }

        if (!v.startsWith("/")) v = "/" + v;

        String url = baseUrl + v;
        System.out.println("[BaseTest] Abrindo: " + url);
        driver.get(url);
    }

    private static String resolveBaseUrl(String raw) {
        String v = (raw == null) ? "" : raw.trim();

        if (v.isEmpty() || (v.startsWith("${") && v.endsWith("}"))) {
            v = "https://parabank.parasoft.com/parabank";
        }
        if (v.startsWith("/")) {
            v = "https://parabank.parasoft.com" + v;
        }
        if (!v.startsWith("http://") && !v.startsWith("https://")) {
            v = "https://" + v;
        }
        if (v.endsWith("/")) v = v.substring(0, v.length() - 1);
        return v;
    }

    private static String propOrDefault(String key, String def) {
        String v = System.getProperty(key);
        if (v == null) return def;
        v = v.trim();
        return v.isEmpty() ? def : v;
    }

    // -------------------------------------------------------------------------
    // Wait + actions — sem alteração de comportamento
    // -------------------------------------------------------------------------

    protected WebElement waitForVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected WebElement waitForClickable(By by) {
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    protected void click(By by) {
        waitForClickable(by).click();
    }

    protected void type(By by, String value) {
        WebElement el = waitForVisible(by);
        el.clear();
        el.sendKeys(value);
    }

    protected boolean isPresent(By locator) {
        try {
            return !driver.findElements(locator).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isLoggedIn() {
        return isPresent(By.cssSelector("#leftPanel a[href*='logout.htm']"))
                || isPresent(By.linkText("Log Out"));
    }

    // -------------------------------------------------------------------------
    // Right panel helpers
    // -------------------------------------------------------------------------

    protected void waitForRightPanelReady() {
        waitForVisible(By.id("rightPanel"));
    }

    protected String rightPanelText() {
        try {
            return waitForVisible(By.id("rightPanel")).getText();
        } catch (Exception e) {
            return "";
        }
    }

    protected List<WebElement> rightPanelRows() {
        try {
            return waitForVisible(By.id("rightPanel")).findElements(By.cssSelector("*"));
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    // -------------------------------------------------------------------------
    // Login / navegação — sem alteração de comportamento
    // -------------------------------------------------------------------------

    protected void loginDefault() {
        login(defaultUser, defaultPass);
    }

    protected void login(String username, String password) {
        if (isLoggedIn()) return;

        open("/index.htm");
        if (isLoggedIn()) return;

        wait.until(d -> isPresent(By.name("username")) || isPresent(By.id("loginPanel")) || isLoggedIn());
        if (isLoggedIn()) return;

        waitForVisible(By.name("username"));
        type(By.name("username"), username);
        type(By.name("password"), password);

        By submit = By.cssSelector("#loginPanel input[type='submit']");
        if (!isPresent(submit)) submit = By.cssSelector("input[type='submit']");
        click(submit);

        wait.until(d -> isLoggedIn() || looksLikeLoginError());

        if (!isLoggedIn()) {
            throw new AssertionError("Falha ao logar. URL=" + driver.getCurrentUrl()
                    + " | rightPanel=" + safeRightPanelText());
        }

        waitForVisible(By.id("leftPanel"));
        waitForVisible(By.id("rightPanel"));
    }

    private boolean looksLikeLoginError() {
        try {
            String page = driver.getPageSource().toLowerCase();
            return page.contains("could not be verified")
                    || page.contains("verification")
                    || page.contains("please enter a username")
                    || page.contains("error")
                    || page.contains("invalid");
        } catch (Exception e) {
            return false;
        }
    }

    private String safeRightPanelText() {
        try {
            return driver.findElement(By.id("rightPanel")).getText();
        } catch (Exception e) {
            return "(rightPanel indisponível)";
        }
    }

    protected void goTo(String linkText) {
        click(By.linkText(linkText));
        waitForVisible(By.id("rightPanel"));
    }

    // Aliases mantidos para compatibilidade com testes existentes
    protected void goToLink(String linkText)     { goTo(linkText); }
    protected void goToLeftMenu(String linkText) { goTo(linkText); }

    protected void waitUntilSelectHasOptions(By selectLocator) {
        wait.until(d -> {
            try {
                Select s = new Select(d.findElement(selectLocator));
                return s.getOptions().size() >= 1;
            } catch (Exception e) {
                return false;
            }
        });
    }

    protected void ensureAtLeastTwoAccounts() {
        if (!isLoggedIn()) loginDefault();

        goTo("Accounts Overview");

        wait.until(d ->
                isPresent(By.id("accountTable")) ||
                d.getPageSource().toLowerCase().contains("accounts overview")
        );

        List<WebElement> accountLinks = driver.findElements(By.cssSelector("#accountTable a"));
        if (accountLinks.size() >= 2) return;

        goTo("Open New Account");
        waitForVisible(By.id("type"));

        By fromAccount = By.id("fromAccountId");
        if (isPresent(fromAccount)) waitUntilSelectHasOptions(fromAccount);

        new Select(driver.findElement(By.id("type"))).selectByValue("1");
        click(By.cssSelector("input.button"));

        wait.until(d ->
                isPresent(By.id("newAccountId")) ||
                d.getPageSource().toLowerCase().contains("account opened") ||
                d.getPageSource().toLowerCase().contains("congratulations")
        );
    }
}
