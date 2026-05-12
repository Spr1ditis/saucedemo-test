package Pages;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;

public class LoginPage {
    //Page constructor
    private final Page page;

    //static locators
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator submitButton;
    private final Locator signupButton;

    public LoginPage(Page page) {
        this.page = page;
        this.emailInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email"));
        this.passwordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password"));
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.signupButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign up"));
    }

    //locators
    public final String loginPageURL = "https://thinking-tester-contact-list.herokuapp.com/";
    public final String wrongLoginError = "Incorrect username or password";
    public final String errorLocator = "#error";

    //Actions
    public void navigateToLoginURL(){
        page.navigate(loginPageURL);
    }

    public void enterEmail(String email){
        emailInput.fill(email);
    }
    public void enterPassword(String password){
        passwordInput.fill(password);
    }
    public void clickSubmit(){
        submitButton.click();
    }
    public void checkWrongLogin(){
        assertThat(page.locator(errorLocator)).containsText(wrongLoginError);
    }
    public void clickSignUp(){
        signupButton.click();
    }



}
