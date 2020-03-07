package application.pages;

import data.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewUserForm extends BasePage {

    public NewUserForm(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // mandatory fields
    @FindBy(id = "id_l.U.cr.login")
    private WebElement loginField;
    @FindBy(id = "id_l.U.cr.password")
    private WebElement passwordField;
    @FindBy(id = "id_l.U.cr.confirmPassword")
    private WebElement repeatPasswordField;

    //optional fields/checkbox
    @FindBy(id = "id_l.U.cr.forcePasswordChange")
    private WebElement forcePswdChangeCheckbox;
    @FindBy(id = "id_l.U.cr.fullName")
    private WebElement fullNameField;
    @FindBy(id = "id_l.U.cr.email")
    private WebElement emailField;
    @FindBy(id = "id_l.U.cr.jabber")
    private WebElement jabberField;

    //buttons
    @FindBy(id = "id_l.U.cr.createUserOk")
    private WebElement submitButon;
    @FindBy(id = "id_l.U.cr.createUserCancel")
    private WebElement cancelButton;

    //locator for dynamic elements (cannot be received with using @FindBy) :
    By errorBulb = By.className("error-bulb2");

    public void fillInUserCreationForm(User user, Boolean forcePwdChange) {
        if (user.getLogin() != null) {
            loginField.sendKeys(user.getLogin());
        }
        if (user.getPassword() != null) {
            passwordField.sendKeys(user.getPassword());
        }
        if (user.getRepeatPassword() != null) {
            repeatPasswordField.sendKeys(user.getRepeatPassword());
        }
        if (forcePwdChange) {
            forcePswdChangeCheckbox.click();
        }
        if (user.getFullName() != null) {
            fullNameField.sendKeys(user.getFullName());
        }
        if (user.getEmail() != null) {
            emailField.sendKeys(user.getEmail());
        }
        if (user.getJabber() != null) {
            jabberField.sendKeys(user.getJabber());
        }
    }

    public void submitUserCreation() {
        submitButon.click();
    }

    public void cancelUserCreation() {
        cancelButton.click();
    }

    public String getErrorMessageOnMandatoryFields() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorBulb));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(errorBulb)).click().build().perform();
        String errorMessage = driver.findElement(By.className("error-tooltip")).getText();
        System.out.println(errorMessage);
        return errorMessage;
    }

}
