package tests;

import data.User;
import data.UserGenerator;
import org.testng.Assert;
import org.testng.annotations.*;

public class NewUserLoginPositiveTests extends BaseTest {

    //expected notifications
    private final String CHANGE_PASSWORD_MSG = "Please change your password!";

    @BeforeMethod
    public void login() {
        app.loginAsRoot();
    }

    @DataProvider
    public Object[] provideUserWithAllFields() {
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUserWithAllOptionalFields();
        return new Object[]{user};
    }

    //checks that created user can login with login name
    @Test(dataProvider = "provideUserWithAllFields")
    public void createUserAndLoginByLoginName(User user) {
        createUser(user, false);
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getLogin(), user.getPassword());
        Assert.assertEquals(app.topMenu.getUserNameFromMenuPanel(), user.getFullName(), "user name doesn't match");
    }

    //checks that created user can login by email
    @Test(dataProvider = "provideUserWithAllFields")
    public void createUserAndLoginByEmail(User user) {
        createUser(user, false);
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getEmail(), user.getPassword());
        Assert.assertEquals(app.topMenu.getUserNameFromMenuPanel(), user.getFullName(), "user name doesn't match");
    }

    //checks that created user is forced to change password if this checkbox was enabled on user creation form
    @Test(dataProvider = "provideUserWithAllFields")
    public void createUserAndLoginWithPwdToChange(User user) {
        createUser(user, true);
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getLogin(), user.getPassword());
        Assert.assertEquals(app.userProfilePage.getPopupNotificationText(), CHANGE_PASSWORD_MSG,"change password message is not shown");
        Assert.assertTrue(app.userProfilePage.isPwdChangeDialogShown());
    }

    //check that user data used during creation match to actual user profile
    @Test(dataProvider = "provideUserWithAllFields")
    public void checkCreatedUserProfile(User user) {
        createUser(user, false);
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        app.loginPage.login(user.getLogin(), user.getPassword());
        app.topMenu.openProfilePage();
        Assert.assertEquals(app.userProfilePage.getUserProfileInfo(), user, "user info doesn't match");
    }

    private void createUser(User user, boolean forcePwdChange) {
        app.navigateToUsersPageViaMenu();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, forcePwdChange);
        app.newUserForm.submitUserCreation();
    }

    @AfterMethod
    // delete created users
    public void teardown(Object[] parameters) {
        User user = (User) parameters[0];
        app.loginAsRoot();
        app.navigateToUsersPage();
        app.manageUsersPage.deleteUserIfExist(user);
    }
}
