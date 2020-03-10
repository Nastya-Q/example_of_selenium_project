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
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "id_l.L.login")
    private WebElement userNameField;

    @FindBy(id = "id_l.L.password")
    private WebElement userPasswordField;

    @FindBy (id = "id_l.L.loginButton")
    private WebElement submitLoginButton;

    public void login (String user, String password) {
        userNameField.sendKeys(user);
        userPasswordField.sendKeys(password);
        submitLoginButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id_l.D.sb.searchPanel")));
    }

}
