package application.pages;

import data.User;
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
    private By topNotificationPopupLocator = By.xpath("//*[@id='__popup__1']//tr[1]/td[2]");
    private By userProfileLogin = By.id("id_l.U.tabs.gst.loginDisplay");
    private By userProfileFullName = By.id("id_l.U.tabs.gst.fullNameText");
    private By userProfileEmail = By.id("id_l.U.tabs.gst.emailText");
    private By userProfileJabber = By.id("id_l.U.tabs.gst.jabberText");


    public String getPopupNotificationText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(topNotificationPopupLocator)).getText();
    }

    public boolean isPwdChangeDialogShown() {
        return driver.findElements(changePwdDialogLocator).size() > 0;
    }

    public User getUserProfileInfo() {
        User userProfileInfo = new User();
        userProfileInfo.setLogin(driver.findElement(userProfileLogin).getAttribute("value"));
        userProfileInfo.setFullName(driver.findElement(userProfileFullName).getAttribute("value"));
        userProfileInfo.setEmail(driver.findElement(userProfileEmail).getAttribute("value"));
        userProfileInfo.setJabber(driver.findElement(userProfileJabber).getAttribute("value"));
        //to make User.equals() method working as null string != ""
        if (userProfileInfo.getEmail().isEmpty()) {
            userProfileInfo.setEmail(null);
        }
        if (userProfileInfo.getJabber().isEmpty()) {
            userProfileInfo.setJabber(null);
        }
        return userProfileInfo;
    }

}
