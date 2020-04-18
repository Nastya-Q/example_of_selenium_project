package tests;

import data.User;
import data.UserGenerator;
import org.testng.Assert;
import org.testng.annotations.*;

public class NewUserLoginPositiveTests extends BaseTest{

    @BeforeMethod
    public void login() {
        app.loginAsRoot();
    }

    @Test
    public void createUserAndLoginByLoginName() {
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUsersWithAllOptionalFields();
        app.navigateToUsersPage();
        app.usersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.usersPage.getUserNameFromEditPage();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getLogin(), user.getPassword());
        Assert.assertEquals(user.getFullName(), app.topMenu.getUserNameFromMenuPanel(), "user name doesn't match");
    }

    @Test
    public void createUserAndLoginByEmail() {
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUsersWithAllOptionalFields();
        app.navigateToUsersPage();
        app.usersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.usersPage.getUserNameFromEditPage();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getEmail(), user.getPassword());
        Assert.assertEquals(user.getFullName(), app.topMenu.getUserNameFromMenuPanel(), "user name doesn't match");
    }

    @AfterMethod
    public void logout() {
        app.logout();
    }
}
