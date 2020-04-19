package application.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserProfilePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public UserProfilePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    //locators
    private By changePwdDialogLocator = By.id("id_l.U.ChangePasswordDialog.changePasswordDlg");
    private By topNotificationPopupLocator = By.className("err");

    public String getPopupNotificationText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(topNotificationPopupLocator)).getText();
    }

    public boolean isPwdChangeDialogShown() {
        return driver.findElements(changePwdDialogLocator).size() > 0;
    }

}
