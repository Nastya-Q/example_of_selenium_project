package application.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TopMenu {
    private WebDriver driver;
    private WebDriverWait wait;

    public TopMenu(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //locators
    private By userNameDropdownMenu = By.xpath("//div[@class='ring-menu__right']//*[contains(@data-ring-dropdown, 'Log out')]");
    private By adminDropdownMenu = By.xpath("//div[@class='ring-menu__right']//*[contains(@data-ring-dropdown, 'Users')]");
    private By manageUsersMenuItem = By.xpath("//*[text()='Users']");
    private By logoutMenuItem = By.xpath("//*[text()='Log out']");
    private By userProfileMenuItem = By.xpath("//*[text()='Profile']");
    private By dashboardLink = By.xpath("//*[@title='YouTrack Dashboard']");

    public void openDashboard() {
        driver.findElement(dashboardLink).click();
    }

    public void logout() {
        driver.findElement(userNameDropdownMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutMenuItem)).click();
    }

    public void openProfilePage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameDropdownMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(userProfileMenuItem)).click();
    }

    public String getUserNameFromMenuPanel() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userNameDropdownMenu)).getText();
    }

    public void openManageUsersPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminDropdownMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(manageUsersMenuItem)).click();
    }
}
