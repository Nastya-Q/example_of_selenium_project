package tests;

import data.User;
import data.UserGenerator;
import org.testng.Assert;
import org.testng.annotations.*;

public class NewUserLoginPositiveTests extends BaseTest{

    //expected notifications
    private final String CHANGE_PASSWORD_MSG = "Please change your password!";

    @BeforeMethod
    public void login() {
        app.loginAsRoot();
    }

    //checks that created user can login with login name
    @Test
    public void createUserAndLoginByLoginName() {
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUsersWithAllOptionalFields();
        app.navigateToUsersPage();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.manageUsersPage.getUserNameFromEditPage();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getLogin(), user.getPassword());
        Assert.assertEquals(user.getFullName(), app.topMenu.getUserNameFromMenuPanel(), "user name doesn't match");
    }

    //checks that created user can login by email
    @Test
    public void createUserAndLoginByEmail() {
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUsersWithAllOptionalFields();
        app.navigateToUsersPage();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.manageUsersPage.getUserNameFromEditPage();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getEmail(), user.getPassword());
        Assert.assertEquals(user.getFullName(), app.topMenu.getUserNameFromMenuPanel(), "user name doesn't match");
    }

    //checks that created user is forced to change password if this checkbox was enabled on user creation form
    @Test
    public void createUserAndLoginWithPwdToChange() {
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUsersWithAllOptionalFields();
        app.navigateToUsersPage();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, true);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.manageUsersPage.getUserNameFromEditPage();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getEmail(), user.getPassword());
        Assert.assertEquals(CHANGE_PASSWORD_MSG, app.userProfilePage.getPopupNotificationText(), "change password message is not shown");
        Assert.assertTrue(app.userProfilePage.isPwdChangeDialogShown());
    }

//    @AfterMethod
//    public void logout() {
//        app.logout();
//    }
}
