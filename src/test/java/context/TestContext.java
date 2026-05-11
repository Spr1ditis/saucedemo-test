package context;

import Pages.LoginPage;
import Pages.StorePage;
import com.microsoft.playwright.*;
import java.sql.Connection;

public class TestContext {
    //core
    public Playwright playwright;
    public Browser browser;
    public Page page;
    public BrowserContext browserContext;
    public APIRequestContext request;

    //Database
    public Connection dbConnection;


    //Pages
    public LoginPage loginPage;
    public StorePage storePage;

    //Initialize pages
    public void initializePages(){
        this.loginPage = new LoginPage(this.page);
        this.storePage = new StorePage(this.page);
    }
}