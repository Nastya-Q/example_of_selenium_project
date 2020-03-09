package tests;

import data.User;
import data.UserGenerator;
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
    @BeforeClass
    public void login() {
        app.loginPage.login("root", "root");
    }

    @DataProvider
    public Iterator<Object[]> provideUsersWithMandatoryAndOptionalFields() {
        List<User> users = new ArrayList<>();
        UserGenerator userGenerator = new UserGenerator();
        users.add(userGenerator.generateUserWithMandatoryFields());
        users.add(userGenerator.generateUserWithFullName());
        users.add(userGenerator.generateUserWithEmail());
        users.add(userGenerator.generateUserWithJabber());
        users.add(userGenerator.generateUsersWithAllOptionalFields());
        return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "provideUsersWithMandatoryAndOptionalFields")
    public void createNewUser(User user) {
        app.usersPage.initNewUserCreation();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.usersPage.getUserNameFromEditPage();
        // check user name on edit page automatically opened after user creation
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
    }

    @AfterMethod
    // delete test user after each creation
    public void teardown(Object[] parameters) {
        User user = (User) parameters[0];
        app.usersPage.deleteUser(user);
    }

}
