package application;

import application.pages.LoginPage;
import application.pages.NewUserForm;
import application.pages.UsersPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationManager {
    private WebDriver driver;
    private WebDriverWait wait;
    private Properties properties;
    private String browser;

    public LoginPage loginPage;
    public UsersPage usersPage;
    public NewUserForm newUserForm;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        if (browser.equals(BrowserType.FIREFOX)) {
            driver = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            driver = new ChromeDriver();
        }
        wait = new WebDriverWait(driver, 10);
        loginPage = new LoginPage(driver, wait);
        usersPage = new UsersPage(driver, wait);
        newUserForm = new NewUserForm(driver, wait);
    }

    public void quit() {
        driver.quit();
    }

    public void navigateToLoginPage(){
        driver.get(properties.getProperty("web.baseUrl") + "login");
    }

    public void navigateToUsersPage() {
        driver.get(properties.getProperty("web.baseUrl") + "users");
    }

    public void loginAsRoot(){
        navigateToLoginPage();
        loginPage.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
