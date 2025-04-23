package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HistoryTest extends BaseTest {

    private JavascriptExecutor scroll;

    @BeforeClass
    public void beforeClass7() {
        System.out.println("(Before Class) Preparing HistoryTest testing...");
        driver = new FirefoxDriver();
        scroll = (JavascriptExecutor) driver;
        driver.get("https://en.wikipedia.org/wiki/Florida");
    }


    // 7.1 - Filter Revisions
    @Test(priority = 1)
    public void filterRevisions() throws InterruptedException {

        // Click on View History
        WebElement viewHistory = driver.findElement(By.linkText("View history"));
        viewHistory.click();
        Thread.sleep(1000);

        // Click on Filter Revisions
        WebElement filterRevisions = driver.findElement(By.xpath("//legend[span[text()='Filter revisions']]"));
        filterRevisions.click();

        // Input date
        WebElement dateInput = driver.findElement(By.cssSelector("div.mw-widget-dateInputWidget-handle"));
        dateInput.sendKeys("2025-04-22");
        Thread.sleep(2000);

        // Input tag
        WebElement tagInput = driver.findElement(By.id("ooui-php-4"));
        tagInput.click();
        tagInput.sendKeys("mw-reverted");
        tagInput.sendKeys(Keys.RETURN);
        Thread.sleep(2000);

        // Press Show Revisions Button
        WebElement revisionbtn = driver.findElement(By.xpath("//span[text()='Show revisions']"));

        scroll.executeScript("arguments[0].scrollIntoView();", revisionbtn);
        Thread.sleep(500);
        revisionbtn.click();
        Thread.sleep(3000);

        // Assert Statements
        WebElement firstRevision = driver.findElement(By.cssSelector("a.mw-changeslist-date"));

        String expectedDate = "09:02, 22 April 2025";
        String actualDate = firstRevision.getText();
        Assert.assertEquals(actualDate, expectedDate, "Wrong date for first entry");

    }

    // 7.2 - Compare Webpage Versions
    @Test(priority = 2)
    public void compareWebpageVersions() throws InterruptedException {

        // Find comparison circle and scroll to it
        WebElement compareCircle = driver.findElement(By.id("mw-oldid-1221638862"));
        Actions actions = new Actions(driver);
        actions.moveToElement(compareCircle);
        Thread.sleep(1000);
        compareCircle.click();

        // Find comparison button
        WebElement compareVersionsBtn = driver.findElement(
                By.xpath("//input[@value='Compare selected revisions']"));
        actions.moveToElement(compareVersionsBtn);
        Thread.sleep(1000);
        compareVersionsBtn.click();
        Thread.sleep(3000);


        // Scroll down to show changes
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        WebElement scroll1 = driver.findElement(By.cssSelector("td.diff-lineno"));
        WebElement scroll2 = driver.findElement(By.xpath(
                "//div[contains(text(), 'population_demonym = Floridian, Floridan')]"));
        WebElement scroll3 = driver.findElement(By.xpath("//div[contains(text(), 'population_rank = 3rd')]"));

        jse.executeScript("arguments[0].scrollIntoView();", scroll1);
        Thread.sleep(2000);
        jse.executeScript("arguments[0].scrollIntoView();", scroll2);
        Thread.sleep(2000);
        jse.executeScript("arguments[0].scrollIntoView();", scroll3);
        Thread.sleep(2000);


        // Switch to visual mode
        WebElement visualBtn = driver.findElement(By.xpath("//div[@role='option']//span[text()='Visual']"));
        actions.moveToElement(visualBtn);
        Thread.sleep(1000);
        visualBtn.click();
        Thread.sleep(10000); // Waits for page to load

        // Scrolls down to showcase changes
        jse.executeScript("window.scrollBy(0,200)", "");
        Thread.sleep(1000);
        jse.executeScript("window.scrollBy(0,400)", "");
        Thread.sleep(1000);
        jse.executeScript("window.scrollBy(0,600)", "");
        Thread.sleep(1000);

        // Switch back to revision history screen
        WebElement viewHistory2 = driver.findElement(By.linkText("View history"));
        jse.executeScript("arguments[0].scrollIntoView();", viewHistory2);
        Thread.sleep(2000);
        viewHistory2.click();
        Thread.sleep(4000);
    }

    // 7.3 - User Contribution Search
    @Test(priority = 3)
    public void userContributionSearch() throws InterruptedException {

        // Click Find edits by user link
        WebElement findEditsByUser = driver.findElement(By.linkText("Find edits by user"));
        scroll.executeScript("arguments[0].scrollIntoView();", findEditsByUser);
        Thread.sleep(500);
        findEditsByUser.click();
        Thread.sleep(2000);

        // Username field
        WebElement username = driver.findElement(By.id("name"));
        username.click();
        username.sendKeys("Needforspeed888");
        Thread.sleep(2000);

        // Max edits shown field
        WebElement maxNumOfEdits = driver.findElement(By.id("max"));
        maxNumOfEdits.click();
        maxNumOfEdits.sendKeys("500");
        Thread.sleep(2000);

        // Submit button
        WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        submitBtn.click();
        Thread.sleep(3000);

        // Assert Statements
        WebElement contributorName = driver.findElement(By.xpath("//b[normalize-space(text())='Needforspeed888']"));
        String actualContributorName = contributorName.getText();

        Assert.assertEquals(actualContributorName, "Needforspeed888", "Wrong contributor name");

        // Selects one of the contributor's past versions
        WebElement contributorPastVersion = driver.findElement(By.linkText("13:34, 03 April 2020"));
        JavascriptExecutor jse2 = (JavascriptExecutor) driver;
        jse2.executeScript("arguments[0].scrollIntoView();", contributorPastVersion);
        Thread.sleep(2000);
        contributorPastVersion.click();
        Thread.sleep(5000);

    }

    // 7.4 - Redirect Pages
    @Test(priority = 4)
    public void redirectPage() throws InterruptedException {

        // Go to "UK" redirect page
        driver.get("https://en.wikipedia.org/w/index.php?title=UK&redirect=no");
        Thread.sleep(2000);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,200)", "");
        Thread.sleep(2000);

        // Go to View History page
        WebElement viewHistory3 = driver.findElement(By.linkText("View history"));
        jse.executeScript("arguments[0].scrollIntoView();", viewHistory3);
        Thread.sleep(1000);
        viewHistory3.click();
        Thread.sleep(2000);

        // Go to previous version
        WebElement previousVersion = driver.findElement(By.partialLinkText("06:48, 12 September 2021"));
        jse.executeScript("arguments[0].scrollIntoView();", previousVersion);
        Thread.sleep(2000);
        previousVersion.click();
        Thread.sleep(2000);

        // Scroll through previous versions
        driver.findElement(By.linkText("← Previous revision")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("← Previous revision")).click();
        Thread.sleep(2000); // This is meant to be executed twice

        for (int i = 0; i < 5; i++) {
            driver.findElement(By.linkText("Newer revision →")).click();
            Thread.sleep(2000);
        }
    }

    // 7.5 - Pageviews Analysis
    @Test(priority = 5)
    public void pageviewsAnalysis() throws InterruptedException {

        // Go to Pageviews page
        driver.get("https://en.wikipedia.org/w/index.php?title=Florida&action=history");
        Thread.sleep(2000);
        driver.findElement(By.linkText("Pageviews")).click();
        Thread.sleep(2000);

        // Latest dropdown
        driver.findElement(By.className("latest-text")).click();
        Thread.sleep(1000);
        driver.findElement(By.linkText("90")).click();
        Thread.sleep(4000);

        // Date Range
        WebElement dateRange = driver.findElement(By.id("range-input"));
        String selector1 = Keys.chord(Keys.CONTROL, "a");
        dateRange.sendKeys(selector1);
        Thread.sleep(1000);
        dateRange.sendKeys(Keys.BACK_SPACE);
        Thread.sleep(1000);

        dateRange.sendKeys("1/1/2024 - 1/1/2025");
        Thread.sleep(2000);
        dateRange.sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        WebElement downloadBtn = driver.findElement(By.cssSelector("div.download-btn-group button.dropdown-toggle"));
        downloadBtn.click();
        Thread.sleep(1000);
        WebElement pngBtn = driver.findElement(By.linkText("PNG"));
        pngBtn.click();
        Thread.sleep(1000);

        // Platform Section
        Select dropdown1 = new Select(driver.findElement(By.id("platform-select")));
        dropdown1.selectByVisibleText("Desktop");
        Thread.sleep(3000);

        downloadBtn.click();
        Thread.sleep(1000);
        pngBtn.click();
        Thread.sleep(1000);

        // Agent Section
        Select dropdown2 = new Select(driver.findElement(By.id("agent-select")));
        dropdown2.selectByVisibleText("Automated");
        Thread.sleep(3000);

        downloadBtn.click();
        Thread.sleep(1000);
        pngBtn.click();
        Thread.sleep(1000);

        // Button Functionality
        driver.findElement(By.id("redirects-checkbox")).click();
        Thread.sleep(2000);

        driver.findElement(By.className("show-labels-option")).click();
        Thread.sleep(2000);

        driver.findElement(By.className("begin-at-zero-option")).click();
        Thread.sleep(2000);

        driver.findElement(By.id("logarithmic-checkbox")).click();
        Thread.sleep(2000);

        driver.findElement(By.className("clear-pages")).click();
        Thread.sleep(3000);
    }

    @AfterClass
    public void afterClass() {
        driver.close();
    }
}
