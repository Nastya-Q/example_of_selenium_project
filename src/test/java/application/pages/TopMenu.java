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
    private By logoutMenuItem = By.xpath("//*[text()='Log out']");
    private By dashboardLink = By.xpath("//*[@title='YouTrack Dashboard']");

    public void openDashboard() {
        driver.findElement(dashboardLink).click();
    }

    public void logout() {
        driver.findElement(userNameDropdownMenu).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutMenuItem)).click();
    }

    public String getUserNameFromMenuPanel() {
        return driver.findElement(userNameDropdownMenu).getText();
    }
}
