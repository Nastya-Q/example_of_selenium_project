package tests;

import data.User;
import data.UserGenerator;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class NewUserFormNegativeTests extends BaseTest {

    //expected error messages on user creation form
    private final String MISSING_PASSWORD_MSG = "Password is required!";
    private final String MISSING_LOGIN_MSG = "Login is required!";
    private final String DIFF_PASSWORDS_MSG = "Password doesn't match!";
    private final String DUPLICATE_USERLOGIN_MSG = "Value should be unique: login";
    private final String SPACE_IN_LOGIN_MSG = "Restricted character ' ' in the name";
    private final String RESTRICTED_LOGINCHARS_MSG = "login shouldn't contain characters \"<\", \"/\", \">\": login";

    @BeforeClass
    public void login() {
        app.loginAsRoot();
        app.navigateToUsersPageViaMenu();
    }

    @DataProvider
    public Object[] provideUserWithMandatoryFields() {
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUserWithMandatoryFields();
        return new Object[]{user};
    }

    @DataProvider
    //the only restricted field on special symbols is login field
    //the only not allowed symbols in login name: <>/ and space
    public Iterator<Object[]> provideUserWithNotAllowedLoginChars() {
        List<User> users = new ArrayList<>();
        UserGenerator userGenerator = new UserGenerator();
        //user with "/"
        User userWithSlash = userGenerator.generateUserWithMandatoryFields();
        userWithSlash.setLogin("with/");
        users.add(userWithSlash);
        //user with "<"
        User userWithLeftAngleBracket = userGenerator.generateUserWithMandatoryFields();
        userWithLeftAngleBracket.setLogin("with<");
        users.add(userWithLeftAngleBracket);
        //user with ">"
        User userWithRightAngleBracket = userGenerator.generateUserWithMandatoryFields();
        userWithRightAngleBracket.setLogin("with>");
        users.add(userWithRightAngleBracket);
        return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> provideUsersWithInvalidEmailAndJabberFormat() {
        List<User> users = new ArrayList<>();
        UserGenerator userGenerator = new UserGenerator();
        users.add(userGenerator.generateUserWithInvalidEmailFormat());
        users.add(userGenerator.generateUserWithInvalidJabberFormat());
        return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Iterator<Object[]> provideUsersWithSpaceInEmailAndJabber() {
        List<User> users = new ArrayList<>();
        UserGenerator userGenerator = new UserGenerator();
        User userWithSpaceInEmail = userGenerator.generateUserWithMandatoryFields();
        userWithSpaceInEmail.setEmail("test name@gmail.com");
        User userWithSpaceInJabber = userGenerator.generateUserWithMandatoryFields();
        userWithSpaceInJabber.setJabber("test name@jabber.com");
        users.add(userWithSpaceInEmail);
        users.add(userWithSpaceInJabber);
        return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }


    //EMPTY MANDATORY FIELDS TESTS
    //login name is empty
    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createNewUserWithoutLogin(User user) {
        user.setLogin(null);
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, MISSING_LOGIN_MSG, "error message doesn't match!");
    }

    //password is empty
    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createNewUserWithoutPassword(User user) {
        user.setPassword(null);
        user.setRepeatPassword(null);
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, MISSING_PASSWORD_MSG, "error message doesn't match!");
    }

    //repeat password is empty
    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createNewUserWithoutPasswordRepeat(User user) {
        user.setRepeatPassword(null);
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, DIFF_PASSWORDS_MSG, "error message doesn't match!");
    }

    //passwords don't match
    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createNewUserWithNotMatchingPasswords(User user) {
        user.setRepeatPassword(user.getPassword() + "diff");
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.newUserForm.getErrorMessageOnMandatoryFields();
        Assert.assertEquals(actualErrorMessage, DIFF_PASSWORDS_MSG, "error message doesn't match!");
    }

    //login name contains space
    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createUserWithSpaceInLogin(User user) {
        user.setLogin("with space");
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.manageUsersPage.getPopupErrorMessage();
        Assert.assertEquals(actualErrorMessage, SPACE_IN_LOGIN_MSG, "error message doesn't match!");
    }

    //login name contains restricted characters (<>/)
    @Test(dataProvider = "provideUserWithNotAllowedLoginChars")
    public void provideUserWithNotAllowedLoginChars(User user) {
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.manageUsersPage.getPopupErrorMessage();
        Assert.assertEquals(actualErrorMessage, RESTRICTED_LOGINCHARS_MSG, "error message doesn't match!");
    }

    //duplicated user name (trying to create user with already existing login name)
    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createUserDuplicatedByLogin(User user) {
        // create user
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getLogin(), "user name doesn't match");
        //repeat the same user creation
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String actualErrorMessage = app.manageUsersPage.getPopupErrorMessage();
        Assert.assertEquals(actualErrorMessage, DUPLICATE_USERLOGIN_MSG, "error message doesn't match!");
    }

    //create user with email which already exists by other existing user - FAILS!
    // (email shouldn't be duplicated as it's user for login functionality)
    @Test(dataProvider = "provideUserWithMandatoryFields")
    public void createUserDuplicatedByEmail(User user) {
        //create user
        user.setEmail(System.currentTimeMillis() + "name@google.com");
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getLogin(), "user name doesn't match");
        //try to create user with the same email
        user.setLogin(user.getLogin() + "new");
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        //check that user with duplicated email wasn't created
        app.navigateToUsersPage();
        Assert.assertFalse(app.manageUsersPage.isUserFoundByLogin(user), "user with duplicated email was created!");
    }


    //email/jabber formats should be validated, but they are not, so test expectedly FAILS!
    @Test(dataProvider = "provideUsersWithInvalidEmailAndJabberFormat")
    public void createUserWithWrongEmailOrJabber(User user) {
        //try to create user
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        //check that users with invalid email/jabber  weren't created:
        app.navigateToUsersPage();
        Assert.assertFalse(app.manageUsersPage.isUserFoundByLogin(user), "user with invalid email or jabber was created!");
    }

    //user email/jabber contains space - FAILS!
    //case 1: email with space, e.g. "test name@gmail.com"
    //case 2: jabber with space "test name@jabber.com"
    @Test(dataProvider = "provideUsersWithSpaceInEmailAndJabber")
    public void createUserWithSpaceInEmailOrJabber(User user) {
        //try to create user
        startNewUserCreation(user);
        app.newUserForm.submitUserCreation();
        //check that users with invalid email/jabber  weren't created:
        app.navigateToUsersPage();
        Assert.assertFalse(app.manageUsersPage.isUserFoundByLogin(user), "user with space in email or jabber was created!");
    }

    private void startNewUserCreation(User user) {
        app.navigateToUsersPage();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, false);
    }

}
