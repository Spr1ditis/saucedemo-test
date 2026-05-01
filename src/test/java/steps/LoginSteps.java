package steps;

import com.microsoft.playwright.options.AriaRole;
import context.TestContext;
import io.cucumber.java.en.*;

import com.microsoft.playwright.APIResponse;
import java.util.regex.Pattern;
import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;


public class LoginSteps {

    private final TestContext context;
    public LoginSteps(TestContext context) {
        this.context = context;
    }

    @Given("User is on login page")
    public void user_is_on_login_page() {
        context.page.navigate("https://www.saucedemo.com/");
    }
    @When("User enters {string} and {string}")
    public void userEntersValidUsernameAndPassword(String username, String password) {
        context.page.locator("[data-test=\"username\"]").fill(username);
        context.page.locator("[data-test=\"password\"]").fill(password);
    }
    @And("User clicks log in button")
    public void user_clicks_log_in_button() {
        context.page.locator("[data-test=\"login-button\"]").click();
    }
    @Then("User is navigated to home page")
    public void user_is_navigated_to_home_page() {
        assertThat(context.page).hasURL(Pattern.compile(".*\\/inventory.*"));
    }

    @Then("API should return successful login response")
    public void check_login_API_response() {
        APIResponse response = context.page.request().get("https://www.saucedemo.com/");
        assertThat(response).isOK();
    }

    @Then("User is informed that account has been locked out")
    public void user_is_locked_out() {
        assertThat(context.page.locator("[data-test=\"error\"]")).containsText("Epic sadface: Sorry, this user has been locked out.");
    }

    @Then("API returns that service is unavailable")
    public void check_login_API_response_unavailable() {
        APIResponse response = context.page.request().get("https://www.saucedemo.com/"); //will fail since demo page is not made for this
        assertThat(response).not().isOK();
    }
    @Then("User is informed that login fails with message: {string}")
    public void login_fails_message(String message) {
        assertThat(context.page.locator("[data-test=\"error\"]")).containsText(message);
    }
    @And("User clicks menu and logout")
    public void click_logout(){
        context.page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Open Menu")).click();
        context.page.locator("[data-test=\"logout-sidebar-link\"]").click();
    }
    @Then("User is in login page")
    public void user_in_login_page_check(){
        assertThat(context.page.locator("[data-test=\"username\"]")).isVisible();
    }
    @Then("User clicks back on browser")
    public void user_click_back_browser(){
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
