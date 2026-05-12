package Pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SignUpPage {
    //Page constructor
    private final Page page;
    private final Locator SubmitNewUserButton;
    private final Locator CancelNewUserButton;
    private final Locator FirstNameInput;
    private final Locator LastNameInput;
    private final Locator EmailInput;
    private final Locator PasswordInput;

    public SignUpPage(Page page) {
        this.page = page;
        this.SubmitNewUserButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.CancelNewUserButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Cancel"));
        this.FirstNameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name"));
        this.LastNameInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name"));
        this.EmailInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email"));
        this.PasswordInput = page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password"));
    }

    //locators

    private final String existingEmailError = "Email address is already in use";

    public void EnterFirstName(String name){
        FirstNameInput.fill(name);
    }
    public void EnterLastName(String surname){
        LastNameInput.fill(surname);
    }
    public void EnterEmail(String email){
        EmailInput.fill(email);
    }
    public void EnterPassword(String password){
        PasswordInput.fill(password);
    }

    public void enterNewUserData(String name, String surname, String email, String password){
        EnterFirstName(name);
        EnterLastName(surname);
        EnterEmail(email);
        EnterPassword(password);
    }
    public void clickSubmit(){
        SubmitNewUserButton.click();
    }
    public void checkEmailExistError(){
        assertThat(page.locator("#error")).containsText(existingEmailError);
    }
}
