package application;

import application.pages.LoginPage;
import application.pages.NewUserForm;
import application.pages.UsersPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ApplicationManager {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage loginPage;
    public UsersPage usersPage;
    public NewUserForm newUserForm;

    public ApplicationManager() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
        loginPage = new LoginPage(driver);
        usersPage = new UsersPage(driver);
        newUserForm = new NewUserForm(driver);
    }

    public void quit() {
        driver.quit();
    }
}
