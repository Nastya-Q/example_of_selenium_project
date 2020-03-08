package application.pages;

import data.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class UsersPage extends BasePage{

    public UsersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "id_l.U.createNewUser")
    private WebElement createNewUserButton;
    @FindBy(id = "id_l.U.queryText")
    private WebElement userSearchField;
    @FindBy(id = "id_l.U.searchButton")
    private WebElement userSearchButton;

    //locator for dynamic elements (cannot be received with using @FindBy):
    private By errorPopupLocator = By.id("__popup__2");
    private By usersCounterLocator = By.xpath("//*[@title='User list']/[]");
    //user list locators:
    private By usersListLocator = By.id("id_l.U.usersList.usersList");
    private By userInfoRowLocator =  By.xpath("//div[@id='id_l.U.usersList.usersList']//tbody/tr");
    private By userLoginNameCellLocator = By.xpath("//div[@id='id_l.U.usersList.usersList']//tbody/tr[1]/td[1]");
    private By userFullNameCellLocator = By.xpath("//div[@id='id_l.U.usersList.usersList']//tbody/tr[1]/td[2]");
    private By userEmailAndJabberCellLocator = By.xpath("//div[@id='id_l.U.usersList.usersList']//tbody/tr[1]/td[3]/div");
    private By userEmailLocator = By.xpath("//div[@id='id_l.U.usersList.usersList']//tbody/tr[1]/td[3]/div[1]");
    private By userJabberLocator = By.xpath("//div[@id='id_l.U.usersList.usersList']//tbody/tr[1]/td[3]/div[2]");


    public void initNewUserCreation() {
        driver.get("http://localhost:8080/users");
        createNewUserButton.click();
//        driver.findElement(By.id("id_l.U.createNewUser")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.cr.createUserDialog")));
//        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("id_l.U.cr.createUserDialog"))));
//        wait.until(ExpectedConditions.elementToBeClickable(createUserDialog));
    }

    public String getUserNameFromEditPage() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.E.AdminBreadcrumb.AdminBreadcrumb")));
        return driver.findElement(By.id("id_l.E.AdminBreadcrumb.AdminBreadcrumb"))
                .findElement(By.cssSelector("li:nth-child(2)")).getText();
    }

    public String getPopupErrorMessage() {
        WebElement errorPopup = wait.until(ExpectedConditions.elementToBeClickable(errorPopupLocator));
        return errorPopup.findElement(By.className("errorSeverity")).getText();
    }

    //todo: fix later to be able to get created users count from left menu
    public Integer getAllUsersCount() {
        String count = driver.findElement(usersCounterLocator).getText();
        return Integer.valueOf(driver.findElement(By.className("admin-menu-counter")).getText());
    }

    public User getCreatedUserInfo(User user) {
        openUsersPage();
        userSearchField.sendKeys(user.getLogin());
        userSearchButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")));
        User foundUser = new User();
        wait.until(ExpectedConditions.elementToBeClickable(userInfoRowLocator));
        foundUser.setLogin(driver.findElement(userLoginNameCellLocator).getText());
        foundUser.setFullName(driver.findElement(userFullNameCellLocator).getText());
        List<WebElement> emailAndJabber = driver.findElements(userEmailAndJabberCellLocator);
        //if email not empty
        if (!emailAndJabber.get(0).getText().equals("")) {
            foundUser.setEmail(driver.findElement(userEmailLocator).getText());
        }
        //if jabber not empty
        if (emailAndJabber.size() == 2) {
            foundUser.setEmail(driver.findElement(userJabberLocator).getText());
        }
        return foundUser;
    }

    public Boolean isUserCreated(User user) {
        openUsersPage();
        userSearchField.sendKeys(user.getLogin());
        userSearchButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")));
        List<WebElement> userInfoRows = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")))
                .findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        if (userInfoRows.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void deleteUser(User user) {
        openUsersPage();
        userSearchField.sendKeys(user.getLogin());
        userSearchButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")));
        WebElement userInfoRow = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")))
                .findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
        userInfoRow.findElement(By.cssSelector("td:nth-child(6)")).findElement(By.cssSelector("a:nth-child(1)")).click();
        driver.switchTo().alert().accept();
    }

    private void openUsersPage() {
        driver.get("http://localhost:8080/users");
    }






}
