package hooks;

import com.microsoft.playwright.options.Cookie;
import context.TestContext;
import com.microsoft.playwright.*;
import io.cucumber.java.*;

import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Hooks {

    private final TestContext context;
    private static List<Cookie> sharedCookies;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before(value = "@ui", order = 1)
    public void setupUI() {
        context.playwright = Playwright.create();
        context.browser = context.playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context.browserContext = context.browser.newContext();
        context.page = context.browserContext.newPage();


        // THE COOKIE STEALER LOGIC
        if (sharedCookies == null) {
            performFullUILogin();
            sharedCookies = context.browserContext.cookies();
        } else {
            context.browserContext.addCookies(sharedCookies);
            context.page.navigate("https://www.saucedemo.com/inventory.html");
        }
    }
    @Before(value = "@pageStart")
    public void NewLoginPage() {
        context.initializePages();
    }

    private void performFullUILogin() {
        context.page.navigate("https://www.saucedemo.com/");
        context.page.locator("[data-test='username']").fill("standard_user");
        context.page.locator("[data-test='password']").fill("secret_sauce");
        context.page.locator("[data-test='login-button']").click();
    }
    @Before("@loggedIn")
    public void ensureAuthenticated() {
        context.page.navigate("https://www.saucedemo.com/");

        if (sharedCookies == null) {
            // THEFT: First time running? Perform full UI login
            performFullUILogin();
            sharedCookies = context.browserContext.cookies();
        } else {
            // INJECTION: Already have cookies? Inject them and teleport
            context.browserContext.addCookies(sharedCookies);
            context.page.navigate("https://www.saucedemo.com/inventory.html");
        }

        // 2. Final verification to ensure we aren't at the login page
        assertThat(context.page).hasURL(Pattern.compile(".*inventory.html"));
    }


    @Before("@api")
    public void setupAPI() {
        if (context.playwright == null) {
            context.playwright = Playwright.create();
        }
        context.request = context.playwright.request().newContext();
    }

    @Before("@db")
    public void setupDB() throws Exception {
        context.dbConnection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/testdb", //change localhost to IP and /testdb to database name
                "user",
                "password"
        );
    }

    @After
    public void endTest() throws Exception {
        if (context.page != null) context.page.close();
        if (context.browser != null) context.browser.close();
        if (context.dbConnection != null) context.dbConnection.close();
    }
}