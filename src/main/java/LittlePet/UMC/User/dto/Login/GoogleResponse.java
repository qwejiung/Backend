package LittlePet.UMC.User.dto.Login;

import java.util.Map;

public class GoogleResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public GoogleResponse(Map<String, Object> attribute) {

        this.attribute = attribute;

    }

    @Override
    public String getProvider() {
        return "google";
    }

    @Override
    public String getProviderId() {
        return attribute.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }


    @Override
    public String getPhone() {
        // Google doesn't provide a phone number in default scope.
        return "Not Provided";
    }

}
