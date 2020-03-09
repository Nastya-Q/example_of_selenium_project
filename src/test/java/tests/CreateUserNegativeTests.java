package tests;

import data.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CreateUserNegativeTests extends BaseTest {
    @BeforeClass
    public void login() {
        app.loginPage.login("root", "root");
    }
    //expected error messages on user creation form
    private final String MISSING_PASSWORD_MSG = "Password is required!";
    private final String MISSING_LOGIN_MSG = "Login is required!";
    private final String DIFF_PASSWORDS_MSG = "Password doesn't match!";
    private final String DUPLICATE_USERLOGIN_MSG = "Value should be unique: login";
    private final String SPACE_IN_LOGIN_MSG = "Restricted character ' ' in the name";
    private final String RESTRICTED_LOGINCHARS_MSG = "login shouldn't contain characters \"<\", \"/\", \">\": login";

    @DataProvider
    public Object[] provideUserWithMandatoryFields() {
        User user = new User();
        user.setLogin(String.format("test%s", System.currentTimeMillis()));
        user.setPassword("test");
        user.setRepeatPassword("test");
        User[] users = {user};
        return users;
    }

    @DataProvider
    //the only restricted field on special symbols is login field
    //the only not allowed symbols in login name: <>/ and space
    public Iterator<Object[]> provideUserWithNotAllowedLoginChars() {
        List<User> users = new ArrayList<>();
        //user with "/"
        User userWithSlash = new User();
        userWithSlash.setLogin("with/");
        users.add(userWithSlash);
        //user with "<"
        User userWithLeftAngleBracket = new User();
        userWithLeftAngleBracket.setLogin("with<");
        users.add(userWithLeftAngleBracket);
        //user with ">"
        User userWithRightAngleBracket = new User();
        userWithRightAngleBracket.setLogin("with>");
        users.add(userWithRightAngleBracket);
        for (User user: users) {
            user.setPassword("test");
            user.setRepeatPassword("test");
        }
        return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    //tests for not filled in mandatory fields
    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createNewUserWithoutLogin(User user) {
        app.usersPage.initNewUserCreation();
        user.setLogin(null);
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, MISSING_LOGIN_MSG, "error message doesn't match!");
    }

    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createNewUserWithoutPassword(User user) {
        app.usersPage.initNewUserCreation();
        user.setPassword(null);
        user.setRepeatPassword(null);
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, MISSING_PASSWORD_MSG, "error message doesn't match!");
    }

    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createNewUserWithoutPasswordRepeat(User user) {
        app.usersPage.initNewUserCreation();
        user.setRepeatPassword(null);
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, DIFF_PASSWORDS_MSG, "error message doesn't match!");
    }

    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createNewUserWithDiffPasswords(User user) {
        app.usersPage.initNewUserCreation();
        user.setRepeatPassword(user.getPassword()+"diff");
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, DIFF_PASSWORDS_MSG, "error message doesn't match!");
    }

    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createUserWithSpaceInLogin(User user){
        app.usersPage.initNewUserCreation();
        user.setLogin("with space");
        app.newUserForm.fillInUserCreationForm(user,false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.usersPage.getPopupErrorMessage();
        Assert.assertEquals(actualErrorMessage, SPACE_IN_LOGIN_MSG, "error message doesn't match!");
    }

    @Test(dataProvider = "provideUserWithNotAllowedLoginChars")
    public void provideUserWithNotAllowedLoginChars(User user){
        app.usersPage.initNewUserCreation();
        app.newUserForm.fillInUserCreationForm(user,false);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.usersPage.getPopupErrorMessage();
        Assert.assertEquals(actualErrorMessage, RESTRICTED_LOGINCHARS_MSG, "error message doesn't match!");
    }

    //duplicated user name
    //todo: optimize
    @Test(dataProvider = "provideUserWithMandatoryFields")
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

    @Test(dataProvider = "provideUserWithMandatoryFields")
    //fill in the form and cancel the user creation:
    public void cancelUserCreation(User user) {
        app.usersPage.initNewUserCreation();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.cancelUserCreation();
        Assert.assertFalse(app.usersPage.isUserCreated(user));
    }




}
