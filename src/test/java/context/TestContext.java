package context;

import com.microsoft.playwright.*;
import java.sql.Connection;

public class TestContext {
    public Playwright playwright;
    public Browser browser;
    public Page page;
    public APIRequestContext request;
    public Connection dbConnection;

    public BrowserContext browserContext;
}