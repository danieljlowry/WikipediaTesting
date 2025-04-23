package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

public class EnglishHomePageTest extends BaseTest {

    @BeforeClass
    public void beforeClass(){
        System.out.println("(Before Class) Preparing LoginTest testing");
        driver = new FirefoxDriver();
        driver.get("https://www.wikipedia.org/");
    }

    //  2.1  - Image Functionality
    //		→ On the home page, click the “English” button (to the top left of the logo)
    //		→ Verify all pictures are on the page (scroll down to see all)
    //		→ Scroll back to the top of the page
    //		→ Click on the first image (top left-most image)
    //		→ Click the full screen button at the top right
    //		→ Exit full screen mode
    //		→ Click through images by clicking the arrow at the right of the screen
    //		→ Exit image menu
    @Test(priority = 1)
    public void imageFunctionality() throws InterruptedException {

        // Wait for page to load
        Thread.sleep(2000);
        // Click the "English" button (top left of logo)
        WebElement englishButton = driver.findElement(By.id("js-link-box-en"));
        englishButton.click();

        // Wait for the English main page to load
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("mp-upper")));
        Assert.assertTrue(driver.getCurrentUrl().contains("en.wikipedia.org"),
                "Failed to navigate to English Wikipedia");


        // Click the first image
        WebElement firstImage = driver.findElement(By.xpath("//img[@alt='Aineta aryballos']"));
        firstImage.click();

        // Wait for the image viewer to load
        Thread.sleep(1000);

        // Click the full screen button
        WebElement fullscreenButton = driver.findElement(
                By.cssSelector("button[title='Show in full screen']"));
        fullscreenButton.click();

        //wait for full screen
        Thread.sleep(1000);

        // Exit full screen mode by clicking the ESC key
        driver.findElement(By.cssSelector("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);

        //wait for exit
        Thread.sleep(2000);

        //reclick picture
        firstImage.click();

        //wait to load
        Thread.sleep(1000);

        // Click through 3 images using the next arrow
        WebElement nextArrow = driver.findElement(
                By.cssSelector("button[title='Show next image']"));
        for (int i = 0; i < 3; i++) {
            nextArrow.click();
            Thread.sleep(1000); // Wait for the image to load
            nextArrow = driver.findElement(
                    By.cssSelector("button[title='Show next image']"));
        }

        // Exit image viewer
        driver.findElement(By.cssSelector("body")).sendKeys(org.openqa.selenium.Keys.ESCAPE);

        //verify we are back on the main page
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("mp-upper")));
        Assert.assertTrue(driver.getCurrentUrl().contains("en.wikipedia.org"), "Failed to navigate back to English Wikipedia");
    }

    //	2.2 - Disabling Page Previews
    //		→ Hover over any linked text (text highlighted blue that indicates that it’s a link)
    //		→ Wait for a submenu to appear
    //		→ Click the cog symbol in the bottom right of the submenu
    //		→ In the popup, uncheck the box
    //		→ Click Save
    //		→ Click Done
    //		→ Rehover over previous linked text to confirm page preview is disabled (not showing)
    @Test(priority = 2)
    public void disablePagePreview() throws InterruptedException {

        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        // Hover over any linked text (Pope Francis is used here for precision on homepage; subject to change)
        WebElement linkedText = driver.findElement(By.xpath("//a[@href='/wiki/Pope_Francis']"));

        // Setup hovering
        Actions actions = new Actions(driver);
        actions.moveToElement(linkedText).perform();

        //wait
        Thread.sleep(1000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click cog symbol
        WebElement cogSymbol = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.mwe-popups-settings-button")));
        cogSymbol.click();

        // Wait for the popup to appear
        Thread.sleep(1000);

        // Uncheck the box in the popup
        WebElement checkbox = driver.findElement(By.id("mwe-popups-settings-page"));
        if (checkbox.isSelected()) {
            checkbox.click();
        }

        // Click Save and Done
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Save')]")));
        saveButton.click();

        WebElement doneButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Done')]")));
        doneButton.click();

        // Confirm page preview is disabled (edit page preview setting shows at bottom of page)
        WebElement editPreviewSettings = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[text()='Edit preview settings']")));
        Assert.assertTrue(editPreviewSettings.isDisplayed(), "Page previews should now be disabled.");
    }

    //	2.3 - Enabling Page Previews
    //		→ Scroll down to the bottom of the home page
    //		→ Click the option in the footer named “Edit preview settings”
    //            → In the popup, check the box
    //		→ Click Save
    //		→ Verify the option in the footer has disappeared
    //		→ Rehover over previous linked text to confirm page preview is enabled (showing up)
    @Test(priority = 3)
    public void enablePagePreview() throws InterruptedException {

        // Scroll to the bottom of the home page
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Click the option in the footer named "Edit preview settings"
        WebElement editPreviewSettings = driver.findElement(By.linkText("Edit preview settings"));
        editPreviewSettings.click();

        // Check the box in the popup
        WebElement checkbox = driver.findElement(By.id("mwe-popups-settings-page"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click Save
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Save')]")));
        saveButton.click();

        // Scroll to top and wait a bit
        js.executeScript("window.scrollTo(0, 0)");
        Thread.sleep(2000); // wait for listeners to be reattached

        // Hover over the Pope Francis link again
        WebElement linkedText = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//a[@href='/wiki/Pope_Francis']")));

        // Scroll it into view
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", linkedText);

        // Perform hover with slight offset
        Actions actions = new Actions(driver);
        actions.moveToElement(linkedText, 2, 2).perform();

        // Wait for the preview popup
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.mwe-popups-container")
        ));

        // Confirm cog symbol appears (optional, additional verification)
        WebElement cogSymbol = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a.mwe-popups-settings-button")
        ));
        Assert.assertTrue(cogSymbol.isDisplayed(), "Page preview should be active after enabling.");
    }

    //	2.4 - Image Download
    //		→ Click on an image (preferably on an article, not random)
    //		→ Click the button labeled “More Details” in the bottom right
    //		→ Click the “Download all sizes” link in the top left of the webpage
    //		→ In the menu, click full “resolution”
    @Test(priority = 4)
    public void imageDownload() throws InterruptedException {

        // Click on the article image (denmark flag)
        driver.get("https://en.wikipedia.org/wiki/1857_in_Denmark");

        // Wait for page to load
        Thread.sleep(2000);
        WebElement articleImage = driver.findElement(By.xpath("//table[contains(@class, 'infobox')]//a/img"));
        articleImage.click();

        // wait for load
        Thread.sleep(1000);

        //scroll down to bottom
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Click the button "More Details" in the bottom right
//        WebElement moreDetailsButton = driver.findElement(
//                By.xpath("//a[contains(@href, 'https://commons.wikimedia.org/wiki/File:Flag_of_Denmark.svg')]")
//        );
//        moreDetailsButton.click();

        // Could not get the "more details" button to be located, so this is in place
        driver.get("https://commons.wikimedia.org/wiki/File:Flag_of_Denmark.svg");

        //wait for load
        Thread.sleep(2000);

        // Click the "Download all sizes" link in the top left of the webpage
        WebElement downloadAllSizesLink = driver.findElement(
                By.cssSelector("span.stockphoto_buttonrow_icon img"));
        downloadAllSizesLink.click();

        // Click full resolution download
        WebElement fullResolutionLink = driver.findElement(
                By.xpath("//a[text()='Full resolution']")
        );
        fullResolutionLink.click();

        // Wait for the download to start
        Thread.sleep(2000);

        // Verify the file is downloaded
        File downloadedFile = new File("/home/cmplonka/Downloads/Flag_of_Denmark.svg");
        Assert.assertTrue(downloadedFile.exists(), "File was not downloaded successfully");
    }

    //  2.5 - Download an article as PDF
    //		→ Go to an article (Japan)
    //		→ Click “Tools” drop down (right of article)
    //		→ Click “Download as PDF”
    //            → Locate “Download” button on new tab
    //		→ Confirm downloaded PDF of article (try to open it if possible)
    @Test(priority = 5)
    public void downloadArticlePDF() throws InterruptedException {
        // Go to an article (Japan)
        driver.get("https://en.wikipedia.org/wiki/Japan");

        // Click "Tools" drop down (right of article)
        WebElement toolsDropdown = driver.findElement(By.id("vector-page-tools-dropdown-checkbox"));
        toolsDropdown.click();

        // Click "Download as PDF"
        WebElement downloadAsPDFLink = driver.findElement(By.id("coll-download-as-rl"));
        downloadAsPDFLink.click();

        // load page
        Thread.sleep(1000);

        // Locate "Download" button
        WebElement downloadButton = driver.findElement(
                By.xpath("//button[.//span[text()='Download']]")
        );
        Assert.assertTrue(downloadButton.isDisplayed(), "Download button is not visible");

        // Click the download button
        downloadButton.click();

        // wait for download
        Thread.sleep(3000);

        // Verify the article was downloaded
        File downloadedFile = new File("/home/cmplonka/Downloads/Japan.pdf");
        Assert.assertTrue(downloadedFile.exists(), "PDF file was not downloaded successfully");
    }

    @AfterClass
    public void afterClass() {
        driver.close();
    }
}
