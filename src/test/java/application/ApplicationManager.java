package application;

import application.pages.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
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
    public ManageUsersPage manageUsersPage;
    public NewUserForm newUserForm;
    public TopMenu topMenu;
    public UserProfilePage userProfilePage;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String targetEnvironment = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", targetEnvironment))));
        if (browser.equals(BrowserType.FIREFOX)) {
            driver = new FirefoxDriver();
        } else if (browser.equals(BrowserType.CHROME)) {
            driver = new ChromeDriver();
        }
        wait = new WebDriverWait(driver, 10);
        loginPage = new LoginPage(driver, wait);
        manageUsersPage = new ManageUsersPage(driver, wait);
        newUserForm = new NewUserForm(driver, wait);
        topMenu = new TopMenu(driver, wait);
        userProfilePage = new UserProfilePage(driver, wait);
    }

    public void quit() {
        driver.quit();
    }

    public void navigateToLoginPage() {
        driver.get(properties.getProperty("web.baseUrl") + "login");
    }

    public void navigateToUsersPage() {
        topMenu.openManageUsersPage();
    }

    public void loginAsRoot() {
        navigateToLoginPage();
        loginPage.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public void logout() {
        topMenu.logout();
    }

    public void takeScreenshot(String methodName) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String screenshotpath = "src/failed_test_screenshots/" + methodName + System.currentTimeMillis() + ".png";
            FileHandler.copy(scrFile, new File(screenshotpath));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
