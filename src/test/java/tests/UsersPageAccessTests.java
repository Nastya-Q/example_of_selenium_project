package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UsersPageAccessTests extends BaseTest{
    private final String NO_PAGE_PERMISSION_MSG = "You have no permissions to view this page";

    //todo: create test user without permissions before test

    //not permitted user cannot access "Create Users" page
    @Test
    public void tryToAccessCreatePageWithoutPermission() {
        app.navigateToLoginPage();
        app.loginPage.login("test12", "test1");
        app.navigateToUsersPageViaDirectLink();
        Assert.assertEquals(NO_PAGE_PERMISSION_MSG, app.commonElements.getErrorPageMessage());
    }
}
