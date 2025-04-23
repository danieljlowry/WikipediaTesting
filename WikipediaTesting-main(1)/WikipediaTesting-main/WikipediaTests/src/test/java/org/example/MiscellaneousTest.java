package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class MiscellaneousTest extends BaseTest {

    @BeforeClass
    public void beforeClass8() {

        System.out.println("(Before Class) Preparing MiscellaneousTest testing...");
        driver = new FirefoxDriver();
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
    }

    // 8.1 - Navigate to random article
    @Test(priority = 1)
    public void navigateToRandomArticle() throws InterruptedException {

        // Top Right Dropdown menu
        WebElement topRightDropdown = driver.findElement(By.id("vector-main-menu-dropdown-checkbox"));
        topRightDropdown.click();
        Thread.sleep(1000);

        // 1st Random Article
        WebElement randomArticleLink = driver.findElement(By.linkText("Random article"));
        randomArticleLink.click();
        String url1 = driver.getCurrentUrl();
        Thread.sleep(2000);

        // 2nd Random Article
        driver.navigate().back();
        Thread.sleep(1000);
        WebElement topRightDropdown2 = driver.findElement(By.id("vector-main-menu-dropdown-checkbox"));
        topRightDropdown2.click();
        Thread.sleep(1000);

        WebElement randomArticleLink2 = driver.findElement(By.linkText("Random article"));
        randomArticleLink2.click();
        String url2 = driver.getCurrentUrl();
        Thread.sleep(2000);

        // 3rd Random Article
        driver.navigate().back();
        Thread.sleep(1000);
        WebElement topRightDropdown3 = driver.findElement(By.id("vector-main-menu-dropdown-checkbox"));
        topRightDropdown3.click();
        Thread.sleep(1000);

        WebElement randomArticleLink3 = driver.findElement(By.linkText("Random article"));
        randomArticleLink3.click();
        String url3 = driver.getCurrentUrl();
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(3000);

        // Assert & Output Statements
        Assert.assertNotEquals(url1, url2, "URL of article 1 and 2 are the same");
        Assert.assertNotEquals(url2, url3, "URL of article 2 and 3 are the same");
        Assert.assertNotEquals(url3, url1, "URL of article 3 and 1 are the same");
        System.out.println("URL of article 1 is " + url1);
        System.out.println("URL of article 2 is " + url2);
        System.out.println("URL of article 3 is " + url3);

    }

    // 8.2 -  Donate page
    @Test(priority = 2)
    public void donatePage() throws InterruptedException {

        // Get to Donate Page
        WebElement topRightDropDown = driver.findElement(By.id("vector-main-menu-dropdown-checkbox"));
        topRightDropDown.click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Donate")).click();
        Thread.sleep(2000);

        // Fill out donate information
        driver.findElement(By.xpath("//label[text()='Give Monthly']")).click();
        Thread.sleep(1000);

        WebElement customAmountBox = driver.findElement(By.id("input_amount_other_box"));
        customAmountBox.click();
        Thread.sleep(1000);
        customAmountBox.sendKeys("200");
        Thread.sleep(1000);

        driver.findElement(By.id("ptf-checkbox")).click();
        Thread.sleep(2000);

        // Click on Paypal option
        driver.findElement(By.className("frb-logo-paypal")).click();
        Thread.sleep(5000);
    }

    // 8.3 -  Reference Desk
    @Test(priority = 3)
    public void referenceDesk() throws InterruptedException{

        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        Thread.sleep(2000);

        // Go to Reference Desk area
        WebElement referenceDeskLink = driver.findElement(By.linkText("Reference desk"));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();", referenceDeskLink);
        Thread.sleep(2000);
        referenceDeskLink.click();
        Thread.sleep(2000);

        // Type "Florida" into first search box
        WebElement topSearchInputBox = driver.findElement(By.cssSelector("input.mw-searchInput"));
        topSearchInputBox.click();
        Thread.sleep(1000);
        topSearchInputBox.sendKeys("Florida");
        Thread.sleep(1000);
        topSearchInputBox.sendKeys(Keys.ENTER);
        Thread.sleep(5000);

        // Clicking 2nd archive link
        WebElement secondArchive = driver.findElement(By.xpath(
                "//a[@title='Wikipedia:Reference desk/Archives/Humanities/2019 April 9']"));
        secondArchive.click();
        Thread.sleep(3000);
        driver.navigate().back();
        Thread.sleep(3000);

        // Click on 2nd archive link "section" tag
        WebElement secondArchiveSection = driver.findElement(By.partialLinkText(
                "Did Mexican revolutionaries ever put forward a claim")
        );
        secondArchiveSection.click();
        Thread.sleep(3000);
        driver.navigate().back();
        Thread.sleep(3000);

        // Search system
        WebElement advancedSearchDropdown = driver.findElement(By.id("ooui-1"));
        advancedSearchDropdown.click();
        Thread.sleep(1000);

        // First Input
        WebElement firstInput = driver.findElement(By.xpath(
                "//input[@type='text' and contains(@class, 'oo-ui-inputWidget-input')]"));
        firstInput.sendKeys("history");
        Thread.sleep(2000);

        // Second Input
        WebElement secondInput = driver.findElement(By.cssSelector("input[placeholder='\"cat loves goat\"']"));
        secondInput.sendKeys("Healthy");
        Thread.sleep(2000);

        // Search Button
        WebElement searchBtn = driver.findElement(By.xpath("//button[span[text()='Search']]"));
        searchBtn.click();
        Thread.sleep(4000);

    }

    // 8.4 - Editing Talk Page (Requires user to be logged in)
    @Test(priority = 4)
    public void editingTalkPage() throws InterruptedException {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        Thread.sleep(2000);

        // Click top left menu
        driver.findElement(By.id("vector-user-links-dropdown-checkbox")).click();
        Thread.sleep(2000);

        // Click "Talk" from the dropdown
        WebElement talkOption = driver.findElement(By.linkText("Talk"));
        talkOption.click();
        Thread.sleep(2000);

        // Click "Create source"
        WebElement createSource = driver.findElement(By.linkText("Edit"));
        createSource.click();
        Thread.sleep(2000);

        driver.findElement(By.tagName("body")).sendKeys(Keys.ENTER);
        Thread.sleep(500);

        // Write in the text box
        WebElement createSourcetTextBox = driver.findElement(By.id("wpTextbox1"));
        createSourcetTextBox.sendKeys("Hello World!");
        Thread.sleep(2000);

        // Preview Changes
        WebElement previewBtn = driver.findElement(By.id("wpPreview"));
        jse.executeScript("arguments[0].scrollIntoView();", previewBtn);
        Thread.sleep(2000);
        previewBtn.click();
        Thread.sleep(2000);

        // Publish Changes
        WebElement publishBtn = driver.findElement(By.id("wpSave"));
        jse.executeScript("arguments[0].scrollIntoView();", publishBtn);
        Thread.sleep(2000);
        publishBtn.click();
        Thread.sleep(2000);

        // Click "Add Topic"
        WebElement addTopicbtn = driver.findElement(By.linkText("Add topic"));
        addTopicbtn.click();
        Thread.sleep(2000);

        // Subject Field
        WebElement subjectField = driver.findElement(By.cssSelector("input[aria-label='Subject']"));
        subjectField.sendKeys("Test Subject");
        Thread.sleep(2000);

        // Description Field
        WebElement descField = driver.findElement(By.cssSelector("div[aria-label='Description'][contenteditable='true']"));
        descField.click();
        Thread.sleep(1000);
        descField.sendKeys("Test Description");
        Thread.sleep(2000);

        WebElement addTopicBtn = driver.findElement(By.xpath("//a[@role='button' and span[text()='Add topic']]"));
        addTopicBtn.click();
        Thread.sleep(5000);

    }

    // 8.5 - Navigating to Sister Projects
    @Test(priority = 5)
    public void navigatingToSisterProjects() throws InterruptedException {

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        Thread.sleep(2000);

        WebElement sister1 = driver.findElement(By.linkText("Wikibooks"));
        jse.executeScript("arguments[0].scrollIntoView();", sister1);
        Thread.sleep(2000);
        sister1.click();
        Thread.sleep(4000);

        driver.navigate().back();
        Thread.sleep(2000);

        WebElement sister2 = driver.findElement(By.linkText("Meta-Wiki"));
        jse.executeScript("arguments[0].scrollIntoView();", sister2);
        Thread.sleep(2000);
        sister2.click();
        Thread.sleep(4000);

        driver.navigate().back();
        Thread.sleep(2000);

        WebElement sister3 = driver.findElement(By.linkText("MediaWiki"));
        jse.executeScript("arguments[0].scrollIntoView();", sister3);
        Thread.sleep(2000);
        sister3.click();
        Thread.sleep(4000);

        driver.navigate().back();
        Thread.sleep(2000);

        WebElement sister4 = driver.findElement(By.linkText("Commons"));
        jse.executeScript("arguments[0].scrollIntoView();", sister4);
        Thread.sleep(2000);
        sister4.click();
        Thread.sleep(4000);
        driver.navigate().back();
        Thread.sleep(2000);

    }

    @AfterClass
    public void afterClass() {
        driver.close();
    }

}
