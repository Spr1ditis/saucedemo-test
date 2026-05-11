package Pages;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;


public class StorePage {

    private final Page page;
    public StorePage(Page page) {
        this.page = page;
    }

    //Locators
    //Input

    //Buttons
    private final String menuButton = "Open Menu";
    private final String sortButton = "[data-test=\"product-sort-container\"]";

    //Menu buttons
    private final String logoutButton = "[data-test=\"logout-sidebar-link\"]";

    //Items

    //Messages, warnings, text
    private final String itemName = ".inventory_item_name";
    private final String itemPrice = ".inventory_item_price";

    //URL, API
    private final String storeTitle = "[class=\"title\"]";


    //Actions
    public void openMenu(){
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(menuButton)).click();
    }
    public void sortByType(String type){
        page.locator(sortButton).selectOption(type);
    }
    //Menu actions
    public void menuLogoutClick(){
        page.locator(logoutButton).click();
    }

    public void logoutShop(){
        openMenu();
        menuLogoutClick();
    }

    //Checks

    public void shopPageValidation(){
        assertThat(page.locator(storeTitle)).hasText("Products");
    }
    public void productListed(String product){
        assertThat(page.getByText(product, new Page.GetByTextOptions().setExact(true))).isVisible();
    }

}
