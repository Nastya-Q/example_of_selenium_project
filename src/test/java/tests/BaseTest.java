package tests;

import application.ApplicationManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    public ApplicationManager app;

    @BeforeClass
    public void start() {
        app = new ApplicationManager();
    }

    @AfterClass
    public void stop() {
        app.quit();
    }
}
