package tests;

import data.User;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CreateUserPositiveTests extends BaseTest {

    private List<User> createdUsers;

    @BeforeClass
    public void login() {
        app.loginPage.login("root", "root");
    }

    @DataProvider
    public Iterator<Object[]> provideUsersWithMandatoryAndOptionalFields() {
        List<User> users = new ArrayList<>();
        //user with mandatory fields
        User userWithMandatoryFields = new User();
        users.add(userWithMandatoryFields);
        //user with optional field: full name
        User userWithFullName = new User();
        userWithFullName.setFullName("test full name" + System.currentTimeMillis());
        users.add(userWithFullName);
        //user with optional field: email
        User userWithEmail = new User();
        userWithEmail.setEmail(System.currentTimeMillis() + "email@google.com");
        users.add(userWithEmail);
        //user with optional field: jabber
        User userWithJabber = new User();
        userWithJabber.setJabber(System.currentTimeMillis() + "user@jabber.org");
        users.add(userWithJabber);
        //user with all optional fields: full name/email/jabber:
        User userWithAllOptionalFields = new User();
        userWithAllOptionalFields.setFullName("test full name" + System.currentTimeMillis());
        userWithAllOptionalFields.setEmail(System.currentTimeMillis() + "email@google.com");
        userWithAllOptionalFields.setJabber(System.currentTimeMillis() + "user@jabber.org");
        users.add(userWithAllOptionalFields);
        //setters for mandatory fields for all test users
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setLogin(String.format("login%s-%s", i, System.currentTimeMillis()));
            users.get(i).setPassword("testpassword");
            users.get(i).setRepeatPassword("testpassword");
        }
        return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "provideUsersWithMandatoryAndOptionalFields")
    public void createNewUser(User user) {
        app.usersPage.initNewUserCreation();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.usersPage.getUserNameFromEditPage();
        // check user name on edit page opened after user creation
        if (user.getFullName() != null) {
            Assert.assertEquals(userNameFromUserEditPage, user.getFullName(), "user name doesn't match");
        } else {
            Assert.assertEquals(userNameFromUserEditPage, user.getLogin(), "user name doesn't match");
        }
        // find created used and check user info in the users list (login, full name, email/jabber)
        Assert.assertTrue(app.usersPage.isUserCreated(user));
        User createdUserInfo = app.usersPage.getCreatedUserInfo(user);
        if (user.getFullName() == null) {
            user.setFullName(user.getLogin()); //if user full name is not defined, then in full name section login name is shown instead
    }
        Assert.assertEquals(createdUserInfo, user, "user info doesn't match!");
        //prepare created users list for next login test
        createdUsers= new ArrayList<User>();
        createdUsers.add(user);
    }

//    @Test
//    public void cancelCreateUser() {
//
//    }


    @AfterMethod
    // delete test user after each creation
    public void teardown(Object[] parameters) {
        User user = (User) parameters[0];
        app.usersPage.deleteUser(user);
    }

}
