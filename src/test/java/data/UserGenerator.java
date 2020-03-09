package data;

import java.util.Random;

public class UserGenerator {
    //user with mandatory fields only
    public User generateUserWithMandatoryFields() {
        Random rand = new Random();
        User user = new User();
        user.setLogin(String.format("login-%s_%s", rand.nextInt(500), System.currentTimeMillis()));
        user.setPassword("testpassword");
        user.setRepeatPassword("testpassword");
        return user;
    }
    //user with optional field: full name
    public User generateUserWithFullName() {
        User user = generateUserWithMandatoryFields();
        user.setFullName("test full name" + System.currentTimeMillis());
        return user;
    }
    //user with optional field: email
    public User generateUserWithEmail() {
        User user = generateUserWithMandatoryFields();
        user.setEmail(System.currentTimeMillis() + "email@google.com");
        return user;
    }
    //user with optional field: jabber
    public User generateUserWithJabber() {
        User user = generateUserWithMandatoryFields();
        user.setJabber(System.currentTimeMillis() + "user@jabber.org");
        return user;
    }
    //user with all optional fields
    public User generateUsersWithAllOptionalFields(){
        User user = generateUserWithMandatoryFields();
        user.setFullName("test full name" + System.currentTimeMillis());
        user.setEmail(System.currentTimeMillis() + "email@google.com");
        user.setJabber(System.currentTimeMillis() + "user@jabber.org");
        return user;
    }

}
