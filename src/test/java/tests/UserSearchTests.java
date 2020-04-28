package tests;

import data.User;
import data.UserGenerator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserSearchTests extends BaseTest {

    private User createdUser;

    @BeforeClass
    public void createUserWithoutPermissions() {
        UserGenerator userGenerator = new UserGenerator();
        createdUser = userGenerator.generateUserWithAllOptionalFields();
        app.loginAsRoot();
        app.navigateToUsersPageViaMenu();
        app.manageUsersPage.openNewUserForm();
        app.newUserForm.fillInUserCreationForm(createdUser, false);
        app.newUserForm.submitUserCreation();
        System.out.println(createdUser.toString());
    }

    //checks that created user can be found by login name and it's info matches with created user
    @Test
    public void findCreatedUserByLogin() {
        app.navigateToUsersPage();
        Assert.assertTrue(app.manageUsersPage.isUserFoundByLogin(createdUser));
        User createdUserInfo = app.manageUsersPage.getUserInfoForProvidedLogin(createdUser);
        Assert.assertEquals(createdUserInfo, createdUserInfo, "user info doesn't match!");
    }

    //checks that created user can be found by email and it's info matches with created user
    @Test
    public void findCreatedUserByEmail() {
        app.navigateToUsersPage();
        Assert.assertTrue(app.manageUsersPage.isUserFoundByEmail(createdUser));
        User createdUserInfo = app.manageUsersPage.getUserInfoForProvidedEmail(createdUser);
        Assert.assertEquals(createdUserInfo, createdUserInfo, "user info doesn't match!");
    }

    //checks that created user can be found by full name and it's info matches with created user
    @Test
    public void findCreatedUserByFullName() {
        app.navigateToUsersPage();
        Assert.assertTrue(app.manageUsersPage.isUserFoundByFullName(createdUser));
        User createdUserInfo = app.manageUsersPage.getUserInfoForProvidedFullName(createdUser);
        Assert.assertEquals(createdUserInfo, createdUserInfo, "user info doesn't match!");
    }

    //search by partial login name

    //search by partial email

    //search by partial full name

    @AfterClass
    public void removeCreatedUser() {
        app.navigateToUsersPage();
        app.manageUsersPage.deleteUserIfExist(createdUser);
    }
}
