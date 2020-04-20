package application.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //locators
    private By loginFieldLocator = By.id("id_l.L.login");
    private By passwordFieldLocator = By.id("id_l.L.password");
    private By submitLoginButtonLocator = By.id("id_l.L.loginButton");
    private By errorBulbLocator = By.className("error-bulb2");
    private By errorHintLocator = By.className("error-tooltip");

    public void login(String user, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginFieldLocator)).sendKeys(user);
        driver.findElement(passwordFieldLocator).sendKeys(password);
        driver.findElement(submitLoginButtonLocator).click();
    }

    public String getLoginErrorHint() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorBulbLocator));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(errorBulbLocator)).click().build().perform();
        return driver.findElement(errorHintLocator).getText();
    }

}
