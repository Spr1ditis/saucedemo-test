package api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.RequestOptions;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static java.lang.String.format;

public class ContactServiceAPI {

    private final APIRequestContext request;

    public ContactServiceAPI(APIRequestContext request) {
        this.request = request;
    }

    public final String contactAPI = "/contactList";
    public final String addUserPayload = "{\"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}";
    public final String addUserPostURL = "/users";
    public final String userPageURL = "/users/me";
    public final String loginPageURL = "/users/login";
    public final String testUser = "test@fake.com";
    public final String testPassword = "myPassword";


    public void checkContactListPageAPI(boolean expected_status){
        APIResponse response = request.get(contactAPI);
        if(expected_status){
            assertThat(response).isOK();}else {
            assertThat(response).not().isOK();
        }
    }

    public String loginAndGetToken(String email, String password) {
        String payload = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

        APIResponse response = request.post(loginPageURL,
                RequestOptions.create().setHeader("Content-Type", "application/json").setData(payload));

        if (response.status() == 200) {
            JsonObject json = JsonParser.parseString(response.text()).getAsJsonObject();
            return json.get("token").getAsString();
        } else {
            throw new RuntimeException("Login failed! Status: " + response.status());
        }
    }
    public String loginDefaultAndGetToken(){
        return loginAndGetToken(testUser,testPassword);
    }
    public void addNewUserAPI(String name, String surname, String email, String password){
        String token = loginDefaultAndGetToken();
        String payload = format(addUserPayload, name, surname, email, password);
        APIResponse response = request.post(addUserPostURL, RequestOptions.create()
                .setData(payload).setHeader("Authorization", "Bearer " + token).setHeader("Content-Type", "application/json"));
        // 4. Check for 201 (Created)
        if(response.status() == 201) {
            System.out.println("User added successfully!");
        } else {
            System.out.println("User not added. Status: " + response.status());
            System.out.println("Response Body: " + response.text());
        }
    }
    public void deleteCurrentUser(String token){
        if (token == null || token.isEmpty()) {
            System.out.println("No token provided. Skipping deletion.");
            return;
        }
        APIResponse response = request.delete(userPageURL, RequestOptions.create().setHeader("Authorization", "Bearer " + token));

        if (response.status() == 200) {
            System.out.println("User deleted successfully.");
        } else {
            System.err.println("Failed to delete user. Status: " + response.status() + " Body: " + response.text());
        }
    }

}
