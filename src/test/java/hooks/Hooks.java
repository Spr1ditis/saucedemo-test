package hooks;

import api.ContactServiceAPI;
import com.microsoft.playwright.options.Cookie;
import context.TestContext;
import com.microsoft.playwright.*;
import io.cucumber.java.*;

import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Hooks {

    private final TestContext context;
    private static List<Cookie> sharedCookies;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before(order = 0)
    public void setup() {
        context.playwright = Playwright.create();
        context.request = context.playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL("https://thinking-tester-contact-list.herokuapp.com"));
        context.contactServiceAPI = new ContactServiceAPI(context.request);
    }
    @Before(value = "@ui", order = 1)
    public void setupUI() {
        context.browser = context.playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context.browserContext = context.browser.newContext();
        context.page = context.browserContext.newPage();

    }
    @Before(value = "@pageStart")
    public void NewLoginPage() {
        context.initializePages();
    }

    private void performFullUILogin() {
        context.page.navigate("https://thinking-tester-contact-list.herokuapp.com/");
        context.page.locator("[data-test='username']").fill("test@fake.com");
        context.page.locator("[data-test='password']").fill("myPassword");
        context.page.locator("[data-test='login-button']").click();
    }
    @Before("@loggedIn")
    public void ensureAuthenticated() {
        context.page.navigate("https://thinking-tester-contact-list.herokuapp.com/");

        if (sharedCookies == null) {
            // Perform login on first run
            performFullUILogin();
            sharedCookies = context.browserContext.cookies();
        } else {
            // repeated login inject session cookie to skip login steps
            context.browserContext.addCookies(sharedCookies);
            context.page.navigate("https://thinking-tester-contact-list.herokuapp.com/");
        }

        // 2. Final verification to ensure we aren't at the login page
        assertThat(context.page).hasURL(Pattern.compile(".*inventory.html"));
    }


    @Before("@db")
    public void setupDB() throws Exception {
        context.dbConnection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/testdb", //change localhost to IP and /testdb to database name
                "user",
                "password"
        );
    }
    @After("@deleteUser")
    public void deleteUser(){
        if (context.lastCreatedEmail != null && context.lastCreatedPassword != null) {

            try {
                System.out.println("Cleanup: Logging in as " + context.lastCreatedEmail);

                // 1. Get a fresh token for the new user
                String cleanupToken = context.contactServiceAPI.loginAndGetToken(
                        context.lastCreatedEmail,
                        context.lastCreatedPassword
                );
                // 2. Delete that user immediately
                context.contactServiceAPI.deleteCurrentUser(cleanupToken);

                System.out.println("Cleanup: Temporary user deleted successfully.");

            } catch (Exception e) {
                System.err.println("Cleanup failed: " + e.getMessage());
            } finally {
                // 3. Clear context so it doesn't interfere with the next test
                context.lastCreatedEmail = null;
                context.lastCreatedPassword = null;
            }
        }
    }

    @After
    public void endTest() throws Exception {
        if (context.page != null) context.page.close();
        if (context.browser != null) context.browser.close();
        if (context.dbConnection != null) context.dbConnection.close();
    }
}