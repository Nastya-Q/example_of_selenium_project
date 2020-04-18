package application.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //locator for dynamic elements (cannot be received with using @FindBy) :
    private By searchPanelLocator = By.id("id_l.D.sb.searchPanel");
    private By loginFieldLocator = By.id("id_l.L.login");
    private By passwordFieldLocator = By.id("id_l.L.password");
    private By submitLoginButtonLocator = By.id("id_l.L.loginButton");

    public void login(String user, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginFieldLocator)).sendKeys(user);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(submitLoginButtonLocator).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(searchPanelLocator));
    }

}
