package tests;

import data.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateUserNegativeTests extends BaseTest {
    //expected error messages on user creation form
    private final String MISSING_PASSWORD_MSG = "Password is required!";
    private final String MISSING_LOGIN_MSG = "Login is required!";
    private final String DIFF_PASSWORDS_MSG = "Password doesn't match!";
    private final String DUPLICATE_USERLOGIN_MSG = "Value should be unique: login";

    @DataProvider
    public Object[] provideCorrectUser() {
        User user = new User();
        user.setLogin("test-" + System.currentTimeMillis());
        user.setPassword("test");
        user.setRepeatPassword("test");
        User[] users = {user};
        return users;
    }

    @BeforeClass
    public void login() {
        app.loginPage.login("root", "root");
    }

    //tests for not filled in mandatory fields
    @Test(dataProvider = "provideCorrectUser")
    public void createNewUserWithoutLogin(User user) {
        app.usersPage.initNewUserCreation();
        user.setLogin(null);
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, MISSING_LOGIN_MSG, "error message doesn't match!");
    }

    @Test(dataProvider = "provideCorrectUser")
    public void createNewUserWithoutPassword(User user) {
        app.usersPage.initNewUserCreation();
        user.setPassword(null);
        user.setRepeatPassword(null);
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, MISSING_PASSWORD_MSG, "error message doesn't match!");
    }

    @Test(dataProvider = "provideCorrectUser")
    public void createNewUserWithoutPasswordRepeat(User user) {
        app.usersPage.initNewUserCreation();
        user.setRepeatPassword(null);
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, DIFF_PASSWORDS_MSG, "error message doesn't match!");
    }

    @Test(dataProvider = "provideCorrectUser")
    public void createNewUserWithDiffPasswords(User user) {
        app.usersPage.initNewUserCreation();
        user.setRepeatPassword(user.getPassword()+"diff");
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, DIFF_PASSWORDS_MSG, "error message doesn't match!");
    }

    //duplicated user name
    @Test(dataProvider = "provideCorrectUser")
    public void createDuplicatedUser(User user) {
        app.usersPage.initNewUserCreation();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.usersPage.getUserNameFromEditPage();
        Assert.assertEquals(userNameFromUserEditPage, user.getLogin(), "user name doesn't match");
        //repeat the same user creation
        app.usersPage.initNewUserCreation();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.usersPage.getPopupErrorMessage();
        Assert.assertEquals(actualErrorMessage, DUPLICATE_USERLOGIN_MSG, "error message doesn't match!");
    }


}
