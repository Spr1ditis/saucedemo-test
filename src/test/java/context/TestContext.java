package context;

import Pages.ContactListPage;
import Pages.LoginPage;
import Pages.SignUpPage;
import api.ContactServiceAPI;
import com.microsoft.playwright.*;
import java.sql.Connection;

public class TestContext {
    //core
    public Playwright playwright;
    public Browser browser;
    public Page page;
    public BrowserContext browserContext;
    public APIRequestContext request;
    public String token;
    public String lastCreatedEmail;
    public String lastCreatedPassword;
    //Database
    public Connection dbConnection;

    //Pages
    public LoginPage loginPage;
    public SignUpPage signupPage;
    public ContactListPage contactlistPage;
    public ContactServiceAPI contactServiceAPI;

    //Initialize pages
    public void initializePages(){
        this.loginPage = new LoginPage(this.page);
        this.signupPage = new SignUpPage(this.page);
        this.contactlistPage = new ContactListPage(this.page);
        this.contactServiceAPI = new ContactServiceAPI(this.request);
    }
}