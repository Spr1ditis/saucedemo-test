package steps;

import com.google.gson.stream.JsonReader;
import com.microsoft.playwright.Locator;
import context.TestContext;
import io.cucumber.java.en.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import utils.ProductReader;

import com.microsoft.playwright.Page;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;
import static org.junit.Assert.assertEquals;

public class ShopSteps {
    private final TestContext context;

    public ShopSteps(TestContext context) {
        this.context = context;
    }

    @Given("User is logged in")
    public void user_is_on_login_page() {
        assertThat(context.page.locator("[class=\"title\"]")).hasText("Products");
    }

    @Then("Product {string} is listed")
    public void product_listed(String product) {
        assertThat(context.page.getByText(product, new Page.GetByTextOptions().setExact(true))).isVisible();
    }

    @When("User clicks to sort products by {string} {string}")
    public void click_sort_by(String name, String type) {
        context.page.locator("[data-test=\"product-sort-container\"]").selectOption(type);
    }

    @Then("Products sorted from {string} {string}")
    public void product_listed(String name, String type) {
        String selector = name.equalsIgnoreCase("Price") ? ".inventory_item_price" : ".inventory_item_name";
        List<String> uiValues = context.page.locator(selector).allInnerTexts();


        List<String> expectedValues = new ArrayList<>(uiValues);

        if (name.equalsIgnoreCase("Price")) {
            //sort by number
            expectedValues.sort(Comparator.comparingDouble(s ->
                    Double.parseDouble(s.replace("$", "").trim())
            ));
        } else {
            //Sort by string
            Collections.sort(expectedValues);
        }
        switch (type.toLowerCase()) {
            case "az", "lohi" -> {
                // All is sorted already
            }
            case "za", "hilo" -> {
                Collections.reverse(expectedValues);
            }
            default -> throw new IllegalArgumentException("Invalid sort type: " + type);
        }

        assertEquals(expectedValues, uiValues);
    }
    @When("User selects {string}")
    public void select_product(String product){
        context.page.getByText(product, new Page.GetByTextOptions().setExact(true)).click();
    }
    @Then("{string} page is opened")
    public void product_page_check(String product){
        //String product_check = String.format("//div[@class='inventory_details_desc_container']/div[text()='%s']",product);
        Locator desc_box = context.page.locator("[class=\"inventory_details_desc_container\"]");
        Locator itemName = desc_box.locator("[data-test=\"inventory-item-name\"]", new Locator.LocatorOptions().setHasText(product));
        //System.out.println(itemName);
        assertThat(itemName).isVisible();
    }
    @Then("{string} description and price is previewed")
    public void product_preview_details(String product){
        ProductReader json = new ProductReader();

            // Simple and clean calls
            String description = json.readDesc(product);
            String price = json.readPrice(product);

            assertThat(context.page.locator("[data-test=\"inventory-item-desc\"]")).hasText(description);
        assertThat(context.page.locator("[data-test=\"inventory-item-price\"]")).hasText("$"+price);

            System.out.println("Product: " + product);
            System.out.println("Desc: " + description);
            System.out.println("Price: " + price);


    }

}
