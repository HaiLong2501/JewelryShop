package model;

/**
 * Profile returned by the Facebook Graph API /me endpoint. Email may be null.
 *
 * @author Hai Long
 */
public class FacebookProfile {

    private String id;
    private String name;
    private String email;

    public FacebookProfile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
