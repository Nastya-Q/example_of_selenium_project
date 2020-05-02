package tests;

import data.User;
import data.UserGenerator;
import org.testng.Assert;
import org.testng.annotations.*;

public class NewUserLoginNegativeTests extends BaseTest {

    //expected notifications
    private final String WRONG_LOGINDATA_MSG = "The username, email or password you entered is incorrect";

    private User createdUser;

    @BeforeClass
    public void createUser() {
        UserGenerator userGenerator = new UserGenerator();
        createdUser = userGenerator.generateUserWithAllOptionalFields();
        app.loginAsRoot();
        app.navigateToUsersPageViaMenu();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(createdUser, false);
        app.newUserForm.submitUserCreation();
    }

    //checks that created user cannot login with correct login and wrong password
    @Test
    public void loginByLoginNameWithWrongPwd() {
        app.navigateToLoginPage();
        createdUser.setPassword(createdUser.getPassword() + "wrongpassword");
        app.loginPage.login(createdUser.getLogin(), createdUser.getPassword());
        Assert.assertEquals(WRONG_LOGINDATA_MSG, app.loginPage.getLoginErrorHint());
    }

    //checks that created user cannot login with correct email and wrong password
    @Test
    public void loginByEmailWithWrongPwd() {
        app.navigateToLoginPage();
        createdUser.setPassword(createdUser.getPassword() + "wrongpassword");
        app.loginPage.login(createdUser.getEmail(), createdUser.getPassword());
        Assert.assertEquals(WRONG_LOGINDATA_MSG, app.loginPage.getLoginErrorHint());
    }

    @AfterClass
    public void removeCreatedUser() {
        app.loginAsRoot();
        app.navigateToUsersPage();
        app.manageUsersPage.deleteUserIfExist(createdUser);
    }

}
