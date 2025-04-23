package org.example;

// Selenium
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Dimension;

// testng
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;



// URL: https://en.wikipedia.org/wiki/Japan
// Timers are going to be generous due to load times among other things
// namely time to explain in-class.
public class AccessibilityTest extends BaseTest{
    // Initialized variables
    WebElement element;
    Actions action;
    String actual;


    // Before Test
    @BeforeClass
    public void beforeTest() {
        // This might not be necessary
        // System.setProperty("webdriver.firefox.driver", "E:\\Coding Software\\Intellj\\Firefox Driver\\geckodriver.exe");
        //
        //
        driver = new FirefoxDriver();
        action = new Actions(driver);
        driver.get("https://en.wikipedia.org/wiki/Japan");
        driver.manage().window().maximize();
    }

    // 6.1 - Language Selector
        @Test(priority = 1)
        public void languageSelector() throws InterruptedException {
            element = driver.findElement(By.id("p-lang-btn"));
            element.click();
            Thread.sleep(2000);
            element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/ul[2]/li[4]/a"));
            element.click();
            Thread.sleep(2000);

            // Check via textbox
            actual = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[3]/div[3]/div[1]/table[1]/tbody/tr[2]/td/table/tbody/tr/td[1]/div/a")).getText();
            Assert.assertEquals(actual, "Bandera");

            // ------------------------------------------------------------------------------------------------------------------------------------------------------------
            // Spanish -> French
            element = driver.findElement(By.id("p-lang-btn"));
            element.click();
            Thread.sleep(2000);
            element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/ul[2]/li[5]/a"));
            element.click();
            Thread.sleep(2000);

            // Check via textbox
            actual = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[3]/div[3]/div[1]/div[1]/table[1]/tbody/tr[1]/td[1]/small/a")).getText();
            Assert.assertEquals(actual, "Drapeau du Japon");

            // ------------------------------------------------------------------------------------------------------------------------------------------------------------
            // French -> German
            element = driver.findElement(By.id("p-lang-btn"));
            element.click();
            Thread.sleep(2000);
            element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/ul[2]/li[2]/a"));
            element.click();
            Thread.sleep(2000);

            // Check via textbox
            actual = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[3]/div[3]/div[1]/table[1]/tbody/tr[4]/td/table/tbody/tr[2]/td[1]/a")).getText();
            Assert.assertEquals(actual, "Flagge");

            // German -> English (test done)
            element = driver.findElement(By.id("p-lang-btn"));
            element.click();
            element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div[1]/ul[2]/li[3]/a"));
            element.click();
            Thread.sleep(1000);
        }

    // 6.2 - Right to Left Languages
        @Test(priority = 2)
        public void rightToLeftLangauges() throws InterruptedException {

            // Selects Arabic language
            element = driver.findElement(By.id("p-lang-btn"));
            element.click();
            element = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/input[2]"));
            element.sendKeys("Arabic");

            // Let's the search bar load the options
            Thread.sleep(1000);

            element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[9]/div/ul/li[1]/a"));
            element.click();

            Thread.sleep(2500);
            driver.get("https://en.wikipedia.org/wiki/Japan");

        }

    // 6.3 - Alt Text
        @Test(priority = 3)
        public void altText() throws InterruptedException {
            Thread.sleep(3000);
            // Japan flag alt text: Flag of Japan
            actual = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[3]/div[3]/div[1]/table[1]/tbody/tr[2]/td/div/div[1]/div[1]/span/a")).getDomAttribute("title");
            Assert.assertEquals(actual, "Flag of Japan");

            // Imperial Seal: Imperial Seal of Japan
            actual = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[3]/div[3]/div[1]/table[1]/tbody/tr[2]/td/div/div[2]/div[1]/span/a")).getDomAttribute("title");
            Assert.assertEquals(actual, "Imperial Seal of Japan");

            // State Seal: Seal of the State of Japan
            actual = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/div[3]/div[3]/div[1]/table[1]/tbody/tr[4]/td/span[3]/a")).getDomAttribute("title");
            Assert.assertEquals(actual, "Seal of the State of Japan");
        }

// 6.4 - Keyboard Shortcuts
    @Test(priority = 4)
    public void keyboardShortcuts() throws InterruptedException {
        Thread.sleep(750);
        action.keyDown(Keys.TAB).perform();
        action.keyDown(Keys.TAB).perform();
        action.keyDown(Keys.TAB).perform();

        Thread.sleep(750); // should be on the wikipedia home icon
        action.keyDown(Keys.TAB).perform();

        Thread.sleep(750); // should be on the search bar
        action.keyDown(Keys.SHIFT);
        action.keyDown(Keys.TAB).perform();

        // History shortcut
        Thread.sleep(1000); // should be back to the wikipedia home icon
        action.keyDown(Keys.ALT); // Should now be holding Alt + Shift
        action.sendKeys("h").perform();

        // Related Changes shortcut
        Thread.sleep(2500);
        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/w/index.php?title=Japan&action=history");
        action.keyDown(Keys.SHIFT);
        action.keyDown(Keys.ALT);
        action.sendKeys("k").perform();

        // Talk shortcut
        Thread.sleep(2500);
        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/wiki/Special:RecentChangesLinked?hidebots=1&hidecategorization=1&hideWikibase=1&target=Japan&limit=50&days=7&urlversion=2");
        action.keyDown(Keys.SHIFT);
        action.keyDown(Keys.ALT);
        action.sendKeys("t").perform();

        // Edit shortcut
        Thread.sleep(2500);
        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/wiki/Talk:Japan");
        action.keyDown(Keys.ALT);
        action.keyDown(Keys.SHIFT);
        action.sendKeys("e").perform();


        Thread.sleep(2500);
        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/wiki/Talk:Japan");

        Thread.sleep(2000);
        driver.get("https://en.wikipedia.org/wiki/Japan");;

        Thread.sleep(3000);

    }

    // 6.5 - Responsive Design
    @Test(priority = 5)
    public void responsiveDesign() throws InterruptedException {
        Thread.sleep(750);
        driver.manage().window().setSize(new Dimension(1920,1080));

        Thread.sleep(2500);
        driver.manage().window().setSize(new Dimension(1440,810));

        Thread.sleep(2500);
        driver.manage().window().setSize(new Dimension(860,540));

        // Begin testing options
        Thread.sleep(2500);
        driver.manage().window().maximize();
        element = driver.findElement(By.id("skin-client-pref-vector-feature-custom-font-size-value-0"));
        element.click();

        Thread.sleep(750);
        element = driver.findElement(By.id("skin-client-pref-vector-feature-limited-width-value-0"));
        element.click();

        Thread.sleep(750);
        element = driver.findElement(By.id("skin-client-pref-skin-theme-value-night"));
        element.click();

        Thread.sleep(750);
        element = driver.findElement(By.xpath("//*[@href='/wiki/Ring_of_Fire']"));
        element.click();

        Thread.sleep(2500);
        element = driver.findElement(By.id("skin-client-pref-vector-feature-custom-font-size-value-1"));
        element.click();

        Thread.sleep(750);
        element = driver.findElement(By.id("skin-client-pref-vector-feature-limited-width-value-1"));
        element.click();

        Thread.sleep(750);
        element = driver.findElement(By.id("skin-client-pref-skin-theme-value-day"));
        element.click();

        Thread.sleep(2500);
    }

    // Not sure how the next test is coded
    // So I'm not sure if I should close the window
    // or not.
    @AfterClass
    public void afterClass() {
        driver.close();
    }
}
