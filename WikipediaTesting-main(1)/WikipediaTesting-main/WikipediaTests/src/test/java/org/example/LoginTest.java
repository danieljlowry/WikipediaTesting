package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest{


    @BeforeClass
    public void beforeClass(){
        System.out.println("(Before Class) Preparing LoginTest testing");
        driver = new FirefoxDriver();
        driver.get("https://en.wikipedia.org/w/index.php?title=Special:UserLogin&returnto=Main+Page");
    }

    //  1.1 - Invalid Login
    //		→ Navigate to Login page
    //		→ Login to Wikipedia using a INVALID username and password
    //		→ Confirm that you are not logged in (error message should appear)
    @Test(priority = 1)
    public void invalidLogin() throws InterruptedException {

        // Initialize test data
        String invalidUsername = "invalidUser";
        String invalidPassword = "invalidPass";

        System.out.println("Invalid Login Test");

        // Get username and password elements
        WebElement username = driver.findElement(By.id("wpName1"));
        WebElement password = driver.findElement(By.id("wpPassword1"));

        // Enter invalid username and password
        username.sendKeys(invalidUsername);
        Thread.sleep(2000);
        password.sendKeys(invalidPassword);
        Thread.sleep(2000);

        // Click the login button
        WebElement loginButton = driver.findElement(By.id("wpLoginAttempt"));
        loginButton.click();
        Thread.sleep(2000);

        // Get error message element
        WebElement errorMessage = driver.findElement(By.className("cdx-message__content"));
        String correctErrorMessage = "Incorrect username or password entered. Please try again.";

        // Verify that the error message is displayed
        Assert.assertTrue(errorMessage.isDisplayed(), "Error message is not displayed.");
        Assert.assertEquals(errorMessage.getText(), correctErrorMessage, "Error message text is incorrect.");
    }

    //	1.2 - Valid Login
    //		→ Now, enter a VALID username and password
    //		→ Confirm you are logged in
    @Test(priority = 2)
    public void validLogin() throws InterruptedException {

        // Initialize test data
        String validUsername = "SoftwareTestingTest";
        String validPassword = "SoftwareTesting123!";

        System.out.println("Valid Login Test");

        // Get username and password elements
        WebElement username = driver.findElement(By.id("wpName1"));
        WebElement password = driver.findElement(By.id("wpPassword1"));

        // Enter valid username and password
        username.clear();
        password.clear();
        Thread.sleep(500);
        username.sendKeys(validUsername);
        Thread.sleep(2000);
        password.sendKeys(validPassword);
        Thread.sleep(2000);

        // Click the login button
        WebElement loginButton = driver.findElement(By.id("wpLoginAttempt"));
        loginButton.click();

        // load page
        Thread.sleep(2000);

        //verify that the user is logged in (see if login button is still available)
        WebElement userSpan = driver.findElement(By.xpath("//span[text()='SoftwareTestingTest']"));
        Assert.assertTrue(userSpan.isDisplayed(), "Failed to login.");
    }

    // 	1.3 - Logout
    //		→ Click the “logout” link (top right)
    //		→ Confirm user is logged out of account
    //		→ Click the “Sign In” link to confirm the user needs to log in again
    @Test(priority = 3)
    public void logout() throws InterruptedException {

        System.out.println("Logout Test");

        //Navigate to log out button
        WebElement dropDown = driver.findElement(By.id("vector-user-links-dropdown-checkbox"));
        dropDown.click();
        Thread.sleep(2000);

        //Logout of account
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, 'Special:UserLogout') and .//span[text()='Log out']]")
        ));
        logoutButton.click();
        Thread.sleep(2000);

        // Verify that the user is logged out
        WebElement loginLink = driver.findElement(By.xpath("//a[contains(@title, \"You're encouraged to log in\")]"));
        Assert.assertTrue(loginLink.isDisplayed(), "User is not logged out.");
    }

    // 	1.4 - “Forgot Password” Functionality
    //		→ Now, click on “Forgot Password”
    //            → Verify that the page is reached
    //		→ Start the process, preferably get through entire process but may not be possible
    @Test(priority = 4)
    public void forgotPassword() throws InterruptedException {

        System.out.println("Forgot Password Test");
        driver.get("https://en.wikipedia.org/w/index.php?title=Special:UserLogin&returnto=Main+Page");

        // Click the "Forgot Password" link
        WebElement forgotPasswordLink = driver.findElement(By.xpath("//a[contains(@href, 'Special:PasswordReset')]"));
        forgotPasswordLink.click();
        Thread.sleep(2000);

        // Verify that the page is reached
        String expectedUrl = "https://auth.wikimedia.org/enwiki/wiki/Special:PasswordReset";
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, expectedUrl, "Forgot Password page is not reached.");

        // Click "reset password" button
        WebElement resetPasswordBtn = driver.findElement(By.id("ooui-php-6"));
        resetPasswordBtn.click();
        Thread.sleep(2000);

        // Verify "password reset has been sent" page is shown
        WebElement requestedReset = driver.findElement(By.id("mw-content-text"));
        Assert.assertTrue(requestedReset.isDisplayed(), "Unable to reach 'You have requested a password reset' page.");
    }

    //	1.5 - “Keep me logged in” functionality
    //		→ Go back to login page
    //		→ Enter valid username and password, don’t log in yet
    //		→ Click the “Keep me logged in (for up to one year)” button
    //		→ Click the “Login” button
    //		→ Confirm you are logged in
    //		→ Exit Wikipedia
    //		→ Reopen Wikipedia
    //		→ Confirm you are still logged in
    @Test(priority = 5)
    public void keepMeLoggedIn() throws InterruptedException {

        System.out.println("Keep Me Logged In Test");
        driver.get("https://en.wikipedia.org/w/index.php?title=Special:UserLogin&returnto=Main+Page");

        // Initialize test data
        String validUsername = "SoftwareTestingTest";
        String validPassword = "SoftwareTesting123!";

        System.out.println("Valid Login Test");

        // Get username and password elements
        WebElement username = driver.findElement(By.id("wpName1"));
        WebElement password = driver.findElement(By.id("wpPassword1"));

        // Enter valid username and password
        username.clear();
        password.clear();
        Thread.sleep(500);
        username.sendKeys(validUsername);
        Thread.sleep(2000);
        password.sendKeys(validPassword);
        Thread.sleep(2000);

        // Click the "Keep me logged in" checkbox
        WebElement keepMeLoggedInCheckbox = driver.findElement(By.id("wpRemember"));
        keepMeLoggedInCheckbox.click();
        Thread.sleep(2000);

        // Click the login button
        WebElement loginButton = driver.findElement(By.id("wpLoginAttempt"));
        loginButton.click();
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //verify that the user is logged in
        WebElement userSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='SoftwareTestingTest']")));
        Assert.assertTrue(userSpan.isDisplayed(), "Failed to login.");

        //Reload Wikipedia
        driver.get("https://en.wikipedia.org/w/index.php?title=Special:UserLogin&returnto=Main+Page");

        // Wait for page response
        Thread.sleep(1000);

        // verify user is still logged in
        userSpan = driver.findElement(By.xpath("//span[text()='SoftwareTestingTest']"));
        Assert.assertTrue(userSpan.isDisplayed(), "Failed to stay logged in.");

    }

    @AfterClass
    public void afterClass() {
        driver.close();
    }
}
