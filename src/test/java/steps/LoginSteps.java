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
        context.loginPage.navigateToLoginURL();
    }

    @When("User enters {string} and {string}")
    public void userEntersValidUsernameAndPassword(String username, String password) {
        context.loginPage.enterEmail(username);
        context.loginPage.enterPassword(password);
    }

    @And("User clicks Submit button")
    public void user_clicks_log_in_button() {
        context.loginPage.clickSubmit();
    }

    @Then("User is logged in their contact list page")
    public void user_is_on_contact_page() {
        context.contactlistPage.checkContactListDisplayed();
    }

    @Then("API should return successful login response")
    public void check_login_API_response() {
        context.contactServiceAPI.checkContactListPageAPI(true);
    }

    @Then("Warning informs user that username or password is incorrect")
    public void wrongLoginDetails() {
        context.loginPage.checkWrongLogin();
    }

    @When("User clicks on Sign up button")
    public void clickSignUp() {
        context.loginPage.clickSignUp();
    }

    @And("Enters {string}, {string}, {string}, {string} and clicks submit")
    public void user_fills_new_data(String username, String surname, String email, String password) {
        context.signupPage.enterNewUserData(username, surname, email, password);
        context.page.pause();
        context.signupPage.clickSubmit();
        context.lastCreatedEmail = email;
        context.lastCreatedPassword = password;
    }

    @Then("User is informed that email address is already in use")
    public void emailTaken(){
        context.signupPage.checkEmailExistError();
    }
    @Given("User with {string}, {string}, {string}, {string} has been added via API")
    public void addUserAPI(String name, String surname, String email, String password){
        context.contactServiceAPI.addNewUserAPI(name, surname, email, password);
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
