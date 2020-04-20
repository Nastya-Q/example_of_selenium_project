package application.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonElements {
    private WebDriver driver;
    private WebDriverWait wait;

    public CommonElements(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //locators
    private By errorPageMessage = By.className("errorPage-title");

    public String getErrorPageMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorPageMessage)).getText();
    }
}
