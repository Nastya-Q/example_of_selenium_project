package tests;

import data.User;
import data.UserGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NewUserLoginNegativeTests extends BaseTest{

    //expected notifications
    private final String WRONG_LOGINDATA_MSG = "The username, email or password you entered is incorrect";

    @BeforeMethod
    public void login() {
        app.loginAsRoot();
    }

    @DataProvider
    public Object[] provideUserWithAllFields() {
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUsersWithAllOptionalFields();
        return new Object[]{user};
    }

    //checks that created user cannot login with correct login and wrong password
    @Test(dataProvider = "provideUserWithAllFields")
    public void createUserAndLoginByLoginNameWithWrongPwd(User user) {
        createUser(user, false);
        // if user full name is empty, then login name is shown instead on all pages, so assigning login name to full name field before asserts
        if (user.getFullName() == null) {
            user.setFullName(user.getLogin());
        }
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        user.setPassword(user.getPassword()+"wrongpassword");
        app.loginPage.login(user.getLogin(), user.getPassword());
        Assert.assertEquals(WRONG_LOGINDATA_MSG, app.loginPage.getLoginErrorHint());
    }

    //checks that created user cannot login with correct email and wrong password
    @Test(dataProvider = "provideUserWithAllFields")
    public void createUserAndLoginByEmailWithWrongPwd(User user) {
        createUser(user, false);
        // if user full name is empty, then login name is shown instead on all pages, so assigning login name to full name field before asserts
        if (user.getFullName() == null) {
            user.setFullName(user.getLogin());
        }
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName());
        app.topMenu.openDashboard(); //to not stay on user edit page
        app.logout();
        user.setPassword(user.getPassword()+"wrongpassword");
        app.loginPage.login(user.getEmail(), user.getPassword());
        Assert.assertEquals(WRONG_LOGINDATA_MSG, app.loginPage.getLoginErrorHint());
    }

    private void createUser(User user, boolean forcePwdChange) {
        app.navigateToUsersPage();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, forcePwdChange);
        app.newUserForm.submitUserCreation();
    }

    //todo: cleanup test data
//    @AfterMethod
//    // delete test user after each creation
//    public void teardown(Object[] parameters) {
//        User user = (User) parameters[0];
//        app.loginAsRoot();
//        app.navigateToUsersPage();
//        app.manageUsersPage.deleteUserIfExist(user);
//    }


}
