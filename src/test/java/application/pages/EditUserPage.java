package application.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditUserPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public EditUserPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //locators
    By userNameHeader = By.xpath("//*[@id='id_l.E.AdminBreadcrumb.AdminBreadcrumb']//li[2]");

    public String getUserName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userNameHeader)).getText();
    }
}
