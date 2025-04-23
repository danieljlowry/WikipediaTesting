package org.example;

// Selenium
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.firefox.FirefoxDriver;

// testng
import org.testng.annotations.*;
import org.testng.Assert;


// Login:
// User: SoftwareTestingTest
// Pass: SoftwareTesting123!

// Test purpose
// Ensure that shortcuts cannot be used for automated navigation to edit pages
// While manually doing things is likely possible, it is far more difficult
// to program a bot to mass edit without pre-defining each edit page manually
// if it lacks this one feature.
// In other words, shortcuts shouldn't be a viable method of navigating to the edit page.
public class EditingTest extends BaseTest {
    // Initialized variables
    WebDriver driver;
    WebElement element;
    Actions action;
    boolean LogTest;


    @BeforeClass
    public void setUp() throws InterruptedException {
        LogTest = false;
        System.setProperty("webdriver.firefox.driver", "E:\\Coding Software\\Intellj\\Firefox Driver\\geckodriver.exe");
        driver = new FirefoxDriver();
        action = new Actions(driver);
        driver.get("https://en.wikipedia.org/wiki/Florida_Gulf_Coast_University"); // change to FGCU https://en.wikipedia.org/wiki/Florida_Gulf_Coast_University
        driver.manage().window().maximize();

        Thread.sleep(1000);
    }

    // 5.1 - shortcutMethod
    // Use a shortcut to try to get into the edit page
    @Test(priority = 1)
    public void shortcutMethod() throws InterruptedException {
        // Navigating to edit
        element = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[1]/div/div[2]/nav[1]/div/div/ul/li[2]/a"));
        element.click();
        Thread.sleep(2000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/w/index.php?title=Florida_Gulf_Coast_University&action=edit");
    }

    // 5.2 - actionMethod
    // Use an action to try to go to the edit page
    @Test(priority = 2)
    public void actionMethod() throws InterruptedException {
        action.keyDown(Keys.ALT);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("e").perform();
        Thread.sleep(2000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/w/index.php?title=Florida_Gulf_Coast_University&action=edit");
    }

    // 5.3 - altPageMethod
    // From another page try to edit the page
    @Test(priority = 3)
    public void altPageMethod() throws InterruptedException {
        action.keyDown(Keys.ALT);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("h").perform();
        Thread.sleep(1000);

        // repeat action method
        action.keyDown(Keys.ALT);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("e").perform();
        Thread.sleep(2000);

        Assert.assertNotEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/w/index.php?title=Florida_Gulf_Coast_University&action=edit");

        Thread.sleep(1000);
        // Direct clicking method
        driver.get("https://en.wikipedia.org/wiki/Florida_Gulf_Coast_University");
        action.keyDown(Keys.ALT);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("h").perform();
        Thread.sleep(2000);

        element = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[1]/div/div[2]/nav[1]/div/div/ul/li[2]/a"));
        element.click();
        Thread.sleep(1000);

        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/w/index.php?title=Florida_Gulf_Coast_University&action=edit");
    }

    // 5.4 - elementLink (replace later?)
    @Test(priority = 4)
    public void linkInsert() {
        element = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[1]/div/div[2]/nav[1]/div/div/ul/li[2]/a"));
        driver.get(element.getAttribute("href"));

        // This method is basically the equivalent of using a direct link
        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/w/index.php?title=Florida_Gulf_Coast_University&action=edit");
    }

    // 5.5 - directLink
    // Tries to get into the webpage directly
    @Test(priority = 5)
    public void directLink() throws InterruptedException {
        driver.get("https://en.wikipedia.org/wiki/florida_Gulf_Coast_University"); // Ensures that the page is the FGCU one
        Thread.sleep(2000);

        driver.get("https://en.wikipedia.org/w/index.php?title=Florida_Gulf_Coast_University&action=edit");
        Thread.sleep(2000);

        // Considering this is a direct link
        // It's probably bypass the bot protection
        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/w/index.php?title=Florida_Gulf_Coast_University&action=edit");
    }

    @AfterClass
    public void quit() throws InterruptedException {
        Thread.sleep(1000);
        driver.close();
    }

}