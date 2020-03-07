package application.pages;

import data.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UsersPage extends BasePage{

    public UsersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "id_l.U.createNewUser")
    private WebElement createNewUserButton;

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

    //fix later
    public Integer getAllUsersCount() {
        String count = driver.findElement(usersCounterLocator).getText();
        return Integer.valueOf(driver.findElement(By.className("admin-menu-counter")).getText());
    }






}
