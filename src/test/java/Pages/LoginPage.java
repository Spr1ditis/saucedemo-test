package Pages;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;

public class LoginPage {
    //Page constructor
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    //Locators
    //Input
    private final String usernameInput = "[data-test='username']";

    //Buttons
    private final String passwordInput = "[data-test='password']";
    private final String loginButton = "[data-test='login-button']";
    //Errors, Warnings
    private final String lockedOutWarningLocator = "[data-test=\"error\"]";
    private final String lockedOutWarningText = "Epic sadface: Sorry, this user has been locked out.";

    //URL, API
    private final String homePageURLpattern = ".*\\/inventory.*";
    private final String sauceURL = "https://www.saucedemo.com/";




    // ACTIONS
    public void goToLoginPage(){
        page.navigate(sauceURL);
    }
    public void enterUsername(String username) {
        page.locator(usernameInput).fill(username);
    }

    public void enterPassword(String password) {
        page.locator(passwordInput).fill(password);
    }

    public void clickLogin() {
        page.locator(loginButton).click();
    }

    //Checks
    public void homePageValidator() {
        assertThat(page).hasURL(Pattern.compile(homePageURLpattern));
    }
    public void APIValidator(boolean expected_status){
        APIResponse response = page.request().get(sauceURL);
        if(expected_status){
        assertThat(response).isOK();}else {
            assertThat(response).not().isOK();
        }
    }
    public void errorValidator(String message){
        assertThat(page.locator(lockedOutWarningLocator)).containsText(message);
    }
    public void lockedOutValidator(){
        errorValidator(lockedOutWarningText);
    }
    public void loginPageValidator(){
        assertThat(page.locator(usernameInput)).isVisible();
    }
}
