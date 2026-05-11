package steps;

import com.microsoft.playwright.options.AriaRole;
import context.TestContext;
import io.cucumber.java.en.*;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;


public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @Given("User is on login page")
    public void user_is_on_login_page() {
        context.loginPage.goToLoginPage();
    }

    @When("User enters {string} and {string}")
    public void userEntersValidUsernameAndPassword(String username, String password) {
        context.loginPage.enterUsername(username);
        context.loginPage.enterPassword(password);
    }

    @And("User clicks log in button")
    public void user_clicks_log_in_button() {
        context.loginPage.clickLogin();
    }

    @Then("User is navigated to home page")
    public void user_is_navigated_to_home_page() {
        context.loginPage.homePageValidator();
    }

    @Then("API should return successful login response")
    public void check_login_API_response() {
        context.loginPage.APIValidator(true);
    }

    @Then("User is informed that account has been locked out")
    public void user_is_locked_out() {
        context.loginPage.lockedOutValidator();
    }

    @Then("API returns that service is unavailable")
    public void check_login_API_response_unavailable() {
        context.loginPage.APIValidator(false);
    }

    @Then("User is informed that login fails with message: {string}")
    public void login_fails_message(String message) {
        context.loginPage.errorValidator(message);
    }

    @And("User clicks menu and logout")
    public void logout_shop() {
        context.storePage.logoutShop();
    }

    @Then("User is in login page")
    public void user_in_login_page_check() {
        context.loginPage.loginPageValidator();
    }

    @Then("User clicks back on browser")
    public void user_click_back_browser() {
        context.page.goBack();
    }


//    @Then("User should exist in database")
//    public void check_user_in_database() throws Exception{
//        PreparedStatement stmt = context.dbConnection.prepareStatement(
//                "SELECT * FROM users WHERE username = ?"
//        );
//
//        stmt.setString(1, "standard_user");
//
//        ResultSet rs = stmt.executeQuery();
//
//        assertTrue(rs.next(), "User not found in database");
//    }
}
