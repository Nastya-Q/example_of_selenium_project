package tests;

import data.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CreateUserPositiveTests extends BaseTest{

    @BeforeClass
    public void login() {
        app.loginPage.login("root", "root");
    }

    @Test
    public void createNewUser() {
        app.usersPage.initNewUserCreation();
        User user = generateTestUser();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.usersPage.getUserNameFromEditPage();
        Assert.assertEquals(userNameFromUserEditPage, user.getLogin(), "user name doesn't match");
    }

    public User generateTestUser() {
        User user = new User();
        user.setLogin("test-" + System.currentTimeMillis());
        user.setPassword("test");
        user.setRepeatPassword("test");
        return user;
    }
}
