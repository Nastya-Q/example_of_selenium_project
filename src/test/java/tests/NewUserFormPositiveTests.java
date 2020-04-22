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

public class NewUserFormPositiveTests extends BaseTest {
    @BeforeClass
    public void login() {
        app.loginAsRoot();
    }

    @DataProvider
    public Iterator<Object[]> provideUsersWithMandatoryAndOptionalFields() {
        List<User> users = new ArrayList<>();
        UserGenerator userGenerator = new UserGenerator();
        users.add(userGenerator.generateUserWithMandatoryFields());
        users.add(userGenerator.generateUserWithFullName());
        users.add(userGenerator.generateUserWithEmail());
        users.add(userGenerator.generateUserWithJabber());
        users.add(userGenerator.generateUserWithAllOptionalFields());
        return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @DataProvider
    public Object[] provideUserWithSpecialSymbols() {
        List<User> users = new ArrayList<>();
        UserGenerator userGenerator = new UserGenerator();
        User user = userGenerator.generateUserWithSpecialSymbolsInAllFieldsExceptLogin();
        return new Object[]{user};
    }

    @DataProvider
    public Iterator<Object[]> provideUsersWithMinFieldsLength() {
        List<User> users = new ArrayList<>();
        UserGenerator userGenerator = new UserGenerator();
        users.add(userGenerator.generateUsersWithMinFieldLengthExceptLogin());
        users.add(userGenerator.generateUsersWithMinFieldLengthExceptFullName());
        users.add(userGenerator.generateUsersWithMinFieldLengthExceptEmail());
        return users.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }


    //check that user created with different options:
    //only mandatory fields, or optional fields 1 by 1, or all optional fields are filled in
    @Test(dataProvider = "provideUsersWithMandatoryAndOptionalFields")
    public void createNewUser(User user) {
        createUser(user);
        // if user full name is empty, then login name is shown instead on all pages, so assigning login name to full name field before asserts
        if (user.getFullName() == null) {
            user.setFullName(user.getLogin());
        }
        // check created user name on edit page automatically opened after user creation
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName(), "user name doesn't match");
        // find created user using search form and check his info in the users list (login, full name, email/jabber)
        app.navigateToUsersPage();
        Assert.assertTrue(app.manageUsersPage.isUserFoundByLogin(user));
        User createdUserInfo = app.manageUsersPage.getUserInfoForProvidedLogin(user);
        Assert.assertEquals(createdUserInfo, user, "user info doesn't match!");
    }

    /* edge case test:
    test for special symbols shows that they are allowed in every field except of login
    (corresponding negative test for login with special symbols is in negative tests)*/
    @Test(dataProvider = "provideUserWithSpecialSymbols")
    public void createUserWithSpecialSymbols(User user) {
        createUser(user);
        // if user full name is empty, then login name is shown instead on all pages, so assigning login name to full name field before asserts
        if (user.getFullName() == null) {
            user.setFullName(user.getLogin());
        }
        // check created user name on edit page automatically opened after user creation
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName(), "user name doesn't match");
        // find created user using search form and check his info in the users list (login, full name, email/jabber)
        app.navigateToUsersPage();
        Assert.assertTrue(app.manageUsersPage.isUserFoundByLogin(user));
        User createdUserInfo = app.manageUsersPage.getUserInfoForProvidedLogin(user);
        Assert.assertEquals(createdUserInfo, user, "user info doesn't match!");
    }

    /* edge case test:
    tests for min fields length: except login, email, full name 1 by 1 to be able to find unique user*/
    @Test(dataProvider = "provideUsersWithMinFieldsLength")
    public void createUserWithMinFieldsLength(User user) {
        createUser(user);
        // if user full name is empty, then login name is shown instead on all pages, so assigning login name to full name field before asserts
        if (user.getFullName() == null) {
            user.setFullName(user.getLogin());
        }
        // check created user name on edit page automatically opened after user creation
        String userNameFromUserEditPage = app.editUserPage.getUserName();
        Assert.assertEquals(userNameFromUserEditPage, user.getFullName(), "user name doesn't match");
        // find created user using search form and check his info in the users list (login, full name, email/jabber)
        app.navigateToUsersPage();
        //search by non 1 symbol field to provide search result uniqueness
        if (user.getLogin().length() > 1) {
            Assert.assertTrue(app.manageUsersPage.isUserFoundByLogin(user));
            User createdUserInfo = app.manageUsersPage.getUserInfoForProvidedLogin(user);
            Assert.assertEquals(createdUserInfo, user, "user info doesn't match!");
        }
        if (user.getEmail().length() > 1) {
            Assert.assertTrue(app.manageUsersPage.isUserFoundByEmail(user));
            User createdUserInfo = app.manageUsersPage.getUserInfoForProvidedEmail(user);
            Assert.assertEquals(createdUserInfo, user, "user info doesn't match!");
        }
        if (user.getFullName().length() > 1) {
            Assert.assertTrue(app.manageUsersPage.isUserFoundByFullName(user));
            User createdUserInfo = app.manageUsersPage.getUserInfoForProvidedFullName(user);
            Assert.assertEquals(createdUserInfo, user, "user info doesn't match!");
        }
    }


    //todo: think about this test
    //check that user is not created when clicking Cancel on New User Form
//    @Test(dataProvider = "provideUserWithMandatoryFields")
//    public void cancelUserCreation(User user) {
//        startNewUserCreation(user);
//        app.newUserForm.cancelUserCreation();
//        Assert.assertFalse(app.manageUsersPage.isUserCreated(user));
//    }

    private void createUser(User user) {
        app.navigateToUsersPage();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(user, false);
        app.newUserForm.submitUserCreation();
    }

    @AfterMethod
    // delete test user after each creation
    public void teardown(Object[] parameters) {
        User user = (User) parameters[0];
        app.navigateToUsersPage();
        app.manageUsersPage.deleteUserIfExist(user);
    }

}
