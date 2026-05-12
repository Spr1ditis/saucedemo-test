package Pages;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ContactListPage {
    //Page constructor
    private final Page page;

    public ContactListPage(Page page) {
        this.page = page;
    }

    //locators


    //Assertions
    public void assertElementByRole(AriaRole role, String element){
        page.pause();
        assertThat(page.getByRole(role,new Page.GetByRoleOptions().setExact(true).setName(element))).isVisible();
    }

    public void checkContactListDisplayed(){
        assertElementByRole(AriaRole.HEADING,"Contact List");
    }



}
