package application.pages;

import data.User;
import org.openqa.selenium.Alert;
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

//    @FindBy(id = "id_l.U.cr.createUserDialog")
//    private WebElement createUserDialog;

    //locator for dynamic elements (cannot be received with using @FindBy) :
    By errorPopupLocator = By.id("__popup__2");
    By usersCounterLocator = By.xpath("//*[@title=\"User list\"]/[]");

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

    //fix later to be able to get created users count from left menu
    public Integer getAllUsersCount() {
        String count = driver.findElement(usersCounterLocator).getText();
        return Integer.valueOf(driver.findElement(By.className("admin-menu-counter")).getText());
    }

    public User findCreatedUser(User user) {
        driver.get("http://localhost:8080/users");
        userSearchField.sendKeys(user.getLogin());
        userSearchButton.click();
        User foundUser = new User();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")));
        WebElement userInfoRow = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")))
                .findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
        String foundUserLogin = userInfoRow.findElement(By.cssSelector("td:nth-child(1)")).getText();
        String foundUserFullName = userInfoRow.findElement(By.cssSelector("td:nth-child(2)")).getText();
        String foundUserEmail = userInfoRow.findElement(By.cssSelector("td:nth-child(3)")).findElement(By.xpath("div[1]")).getText();
        //if there is second div with jabber
        List<WebElement> emailAndJabber = userInfoRow.findElement(By.cssSelector("td:nth-child(3)")).findElements(By.tagName("div"));
        if (!emailAndJabber.get(0).getText().equals("")) {
            foundUser.setEmail(emailAndJabber.get(0).getText());
        }
        if (emailAndJabber.size() == 2) {
            foundUser.setJabber(emailAndJabber.get(1).getText());
        }
        foundUser.setLogin(foundUserLogin);
        foundUser.setFullName(foundUserFullName);
        return foundUser;
    }

    public void deleteUser(User user) {
        driver.get("http://localhost:8080/users");
        userSearchField.sendKeys(user.getLogin());
        userSearchButton.click();
        //*[@id="id_l.U.usersList.usersList"]/table/tbody/tr[1]/td[6]
        wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")));
        WebElement userInfoRow = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_l.U.usersList.usersList")))
                .findElement(By.tagName("tbody")).findElement(By.tagName("tr"));
        userInfoRow.findElement(By.cssSelector("td:nth-child(6)")).findElement(By.cssSelector("a:nth-child(1)")).click();
        driver.switchTo().alert().accept();
    }






}
