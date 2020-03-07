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






}
