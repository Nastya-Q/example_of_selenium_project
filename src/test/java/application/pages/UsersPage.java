package application.pages;

import data.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class UsersPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public UsersPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "id_l.U.createNewUser")
    private WebElement createNewUserButton;
    @FindBy(id = "id_l.U.queryText")
    private WebElement userSearchField;
    @FindBy(id = "id_l.U.searchButton")
    private WebElement userSearchButton;

    //locators for dynamic elements (cannot be received with using @FindBy):
    private By errorPopupLocator = By.xpath("//*[@id='__popup__2']//li[@class='errorSeverity']");
    private By usersCounterLocator = By.xpath("//*[@title='User list']/[]");
    //user list locators:
    private By usersListLocator = By.id("id_l.U.usersList.usersList");
    private String userTableBodyRef = "//div[@id='id_l.U.usersList.usersList']//tbody";
    private By userInfoRowLocator = By.xpath(userTableBodyRef + "/tr");
    private By userLoginNameCellLocator = By.xpath(userTableBodyRef + "/tr[1]/td[1]");
    private By userFullNameCellLocator = By.xpath(userTableBodyRef + "/tr[1]/td[2]");
    private By userEmailAndJabberCellLocator = By.xpath(userTableBodyRef + "/tr[1]/td[3]/div");
    private By userEmailLocator = By.xpath(userTableBodyRef + "/tr[1]/td[3]/div[1]");
    private By userJabberLocator = By.xpath(userTableBodyRef + "/tr[1]/td[3]/div[2]");
    private By deleteUserLocator = By.xpath(userTableBodyRef + "/tr[1]/td[6]/a[1]");


    public void initNewUserCreation() {
        createNewUserButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.cr.createUserDialog")));
    }

    public String getUserNameFromEditPage() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.E.AdminBreadcrumb.AdminBreadcrumb")));
        return driver.findElement(By.id("id_l.E.AdminBreadcrumb.AdminBreadcrumb"))
                .findElement(By.cssSelector("li:nth-child(2)")).getText();
    }

    public String getPopupErrorMessage() {
        return wait.until(ExpectedConditions.elementToBeClickable(errorPopupLocator)).getText();
    }

    //todo: fix later to be able to get created users count from left menu
    public Integer getAllUsersCount() {
        String count = driver.findElement(usersCounterLocator).getText();
        return Integer.valueOf(driver.findElement(By.className("admin-menu-counter")).getText());
    }

    public User getCreatedUserInfo(User user) {
        userSearchField.clear();
        userSearchField.sendKeys(user.getLogin());
        userSearchButton.click();
        // only for google chrome: wait until user list re-draws
        if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().equalsIgnoreCase("chrome")) {
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(usersListLocator)));
        }
        User foundUser = new User();
        foundUser.setLogin(driver.findElement(userLoginNameCellLocator).getText());
        foundUser.setFullName(driver.findElement(userFullNameCellLocator).getText());
        List<WebElement> emailAndJabber = driver.findElements(userEmailAndJabberCellLocator);
        //if email not empty
        if (!emailAndJabber.get(0).getText().equals("")) {
            foundUser.setEmail(driver.findElement(userEmailLocator).getText());
        }
        //if jabber not empty
        if (emailAndJabber.size() == 2) {
            foundUser.setJabber(driver.findElement(userJabberLocator).getText());
        }
        return foundUser;
    }

    public Boolean isUserCreated(User user) {
        userSearchField.sendKeys(user.getLogin());
        userSearchButton.click();
        // only for google chrome: wait until user list re-draws
        if (((RemoteWebDriver) driver).getCapabilities().getBrowserName().equalsIgnoreCase("chrome")) {
            wait.until(ExpectedConditions.stalenessOf(driver.findElement(usersListLocator)));
        }
        List<WebElement> userInfoRows = driver.findElements(userInfoRowLocator);
        return userInfoRows.size() > 0;
    }

    public void deleteUser(User user) {
        userSearchField.sendKeys(user.getLogin());
        userSearchButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(usersListLocator));
        WebElement userInfoRow = wait.until(ExpectedConditions.elementToBeClickable(userInfoRowLocator));
        userInfoRow.findElement(deleteUserLocator).click();
        driver.switchTo().alert().accept();
    }
}
