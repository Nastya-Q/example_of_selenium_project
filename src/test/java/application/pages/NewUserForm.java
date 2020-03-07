package application.pages;

import data.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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



    public void fillInUserCreationForm(User user, Boolean forcePwdChange) {
        loginField.sendKeys(user.getLogin());
        passwordField.sendKeys(user.getPassword());
        repeatPasswordField.sendKeys(user.getPassword());
        if (forcePwdChange) {
            forcePswdChangeCheckbox.click();
        }
//        fullNameField.sendKeys(user.getFullName());
//        emailField.sendKeys(user.getEmail());
//        jabberField.sendKeys(user.getJabber());
    }

    public void submitUserCreation() {
        submitButon.click();
    }

    public void cancelUserCreation() {
        cancelButton.click();
    }

    public String getErrorMessage() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("error-bulb2")));
        String errorMessage = driver.findElement(By.className("error-tooltip")).getText();
        return errorMessage;
    }

}
