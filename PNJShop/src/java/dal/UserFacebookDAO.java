package dal;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import config.FacebookConfig;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import model.FacebookProfile;
import org.apache.http.client.fluent.Request;

/**
 * Calls to the Facebook Graph API for the login flow.
 *
 * @author Hai Long
 */
public class UserFacebookDAO {

    private static final int TIMEOUT_MS = 10000;

    public String getToken(String code) throws IOException {
        String link = FacebookConfig.TOKEN_URL
                + "?client_id=" + encode(FacebookConfig.getAppId())
                + "&redirect_uri=" + encode(FacebookConfig.getRedirectUri())
                + "&client_secret=" + encode(FacebookConfig.getAppSecret())
                + "&code=" + encode(code);
        String response = get(link);

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        JsonElement token = jobj == null ? null : jobj.get("access_token");
        if (token == null || token.isJsonNull()) {
            throw new IOException("Facebook did not return an access token");
        }
        return token.getAsString();
    }

    public FacebookProfile getProfile(String accessToken) throws IOException {
        String link = FacebookConfig.ME_URL
                + "?fields=id,name,email&access_token=" + encode(accessToken);
        String response = get(link);

        FacebookProfile profile = new Gson().fromJson(response, FacebookProfile.class);
        if (profile == null || profile.getId() == null) {
            throw new IOException("Facebook did not return a user id");
        }
        return profile;
    }

    private String get(String link) throws IOException {
        return Request.Get(link)
                .connectTimeout(TIMEOUT_MS)
                .socketTimeout(TIMEOUT_MS)
                .execute().returnContent().asString(StandardCharsets.UTF_8);
    }

    private static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}
