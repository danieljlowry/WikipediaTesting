package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ArticleTest extends BaseTest{

    @BeforeClass
    void classSetup(){
        driver = new FirefoxDriver();
        driver.get("https://www.wikipedia.org/");

    }

    @Test (priority = 1)
    void test1() throws InterruptedException {
        driver.findElement(By.id("searchInput")).sendKeys("Japan");
        Thread.sleep(2000);
        Assert.assertNotNull(driver.findElement(By.className("suggestions-dropdown")), "Dropdown not present.");
        Thread.sleep(1000);
        driver.findElement(By.className("suggestion-link")).click();
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(1000);
        driver.findElement(By.id("searchInput")).sendKeys("Japan", Keys.RETURN);
        Thread.sleep(3000);
    }

    @Test (priority = 2)
    void test2() throws InterruptedException{
        driver.navigate().to("https://en.wikipedia.org/wiki/Japan");
        String japanTitle = driver.getTitle();
        Thread.sleep(500);
        driver.findElement(By.id("vector-page-tools-dropdown-checkbox")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("t-permalink")).click();
        Thread.sleep(2000);
        Assert.assertNotEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/wiki/Japan", "Links are not different.");
        Assert.assertEquals(driver.getTitle(), japanTitle, "Pages are not the same.");
    }

    @Test (priority = 3)
    void test3() throws InterruptedException{
        driver.navigate().to("https://en.wikipedia.org/wiki/Japan");
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@href='/wiki/File:Kimi_ga_Yo_instrumental.ogg']")).click();
        Thread.sleep(2000);
        Assert.assertNotNull(driver.findElement(By.id("mwe_player_0")));
        Thread.sleep(5000);
    }

    @Test (priority = 4)
    void test4() throws InterruptedException{
        driver.navigate().to("https://en.wikipedia.org/wiki/Japan");
        Thread.sleep(500);
        driver.findElement(By.id("vector-page-tools-dropdown-checkbox")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("t-urlshortener-qrcode")).click();
        Thread.sleep(3000);
    }

    @Test (priority = 5)
    void test5() throws InterruptedException{
        driver.navigate().to("https://en.wikipedia.org/wiki/Main_Page");
        Thread.sleep(2000);
        driver.findElement(By.id("pt-login-2")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("wpName1")).sendKeys("SoftwareTestingTest");
        driver.findElement(By.id("wpPassword1")).sendKeys("SoftwareTesting123!");
        driver.findElement(By.id("wpLoginAttempt")).click();
        Thread.sleep(2000);
        driver.navigate().to("https://en.wikipedia.org/wiki/Japan");
        Thread.sleep(2000);
        driver.findElement(By.id("ca-watch")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@href='/wiki/Special:Watchlist']")).click();
        Thread.sleep(2000);
        Assert.assertNotNull(driver.findElement(By.linkText("Japan")), "Page not added to Watchlist.");
        driver.findElement(By.xpath("//*[@href='/wiki/Special:EditWatchlist']")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Japan")).click();
        Thread.sleep(500);
        Assert.assertEquals(driver.getCurrentUrl(), "https://en.wikipedia.org/wiki/Japan", "Does not go to correct article.");
        Thread.sleep(2000);
        driver.navigate().back();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@value='Japan']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@value='Remove titles']")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("Special:Watchlist")).click();
        Thread.sleep(2000);
    }

    @AfterClass
    public void afterClass() {
        driver.close();
    }
}
