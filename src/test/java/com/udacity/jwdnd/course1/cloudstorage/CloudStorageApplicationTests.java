package com.udacity.jwdnd.course1.cloudstorage;

import java.io.File;
import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.udacity.jwdnd.course1.cloudstorage.selenium.HomePageObject;
import com.udacity.jwdnd.course1.cloudstorage.selenium.LoginPageObject;
import com.udacity.jwdnd.course1.cloudstorage.selenium.SignupPageObject;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    private static final Logger LOG = LogManager.getLogger(CloudStorageApplicationTests.class);

    @LocalServerPort
    private int port;

    private WebDriver driver;
    private HomePageObject homePageObject;
    private LoginPageObject loginPageObject;
    private SignupPageObject signupPageObject;

    private final String testFirstname = "testFirstname";
    private final String testSurname = "testSurname";
    private final String testUsername = "testUsername";
    private final String testUsername2 = "testUsername2";
    private final String testUsername3 = "testUsername3";
    private final String testPassword = "testPassword";
    private final String testNoteTitle = "testNoteTitle";
    private final String testNoteDescription = "testNoteDescription";
    private final String testNoteTitle2 = "testNoteTitle2";
    private final String testNoteDescription2 = "testNoteDescription2";
    private final String testCredentialUrl = "testCredentialUrl";
    private final String testCredentialUsername = "testCredentialUsername";
    private final String testCredentialPassword = "testCredentialPassword";
    private final String testCredentialUrl2 = "testCredentialUrl2";
    private final String testCredentialUsername2 = "testCredentialUsername2";

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
     * checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
        signupPageObject = new SignupPageObject(driver);

        signupPageObject.signUp(firstName, lastName, userName, password);

        /*
         * Check that the sign up was successful. // You may have to modify the element
         * "success-msg" and the sign-up // success message below depening on the rest
         * of your code.
         */

        LOG.info(MessageFormat.format("The current title is: {0}", driver.getTitle()));

        webDriverWait.until(ExpectedConditions.titleContains("Login"));

        Assertions
                .assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }

    /**
     * PLEASE DO NOT DELETE THIS method. Helper method for Udacity-supplied sanity
     * checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        loginPageObject = new LoginPageObject(driver);

        loginPageObject.enterUsernameAndPassword(userName, password);

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code. This test is provided by Udacity to perform some basic
     * sanity testing of your code to ensure that it meets certain rubric criteria.
     * 
     * If this test is failing, please ensure that you are handling redirecting
     * users back to the login page after a succesful sign up. Read more about the
     * requirement in the rubric: https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code. This test is provided by Udacity to perform some basic
     * sanity testing of your code to ensure that it meets certain rubric criteria.
     * 
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * 
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code. This test is provided by Udacity to perform some basic
     * sanity testing of your code to ensure that it meets certain rubric criteria.
     * 
     * If this test is failing, please ensure that you are handling uploading large
     * files (>1MB), gracefully in your code.
     * 
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload
     * Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("file-success-msg")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

    private void doLogout() {
        HomePageObject homePageObject = new HomePageObject(driver);
        homePageObject.logout();
    }

    @Test
    public void testSignupAndLoginFlow() {
        // Verify signup + login + home access
        doMockSignUp(testFirstname, testSurname, testUsername, testPassword);
        doLogIn(testUsername, testPassword);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        Assertions.assertEquals("Home", driver.getTitle());

        // Verify logout + home no access
        doLogout();
        driver.get("http://localhost:" + this.port + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));

        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testAddingEditingDeletingNotes() throws InterruptedException {
        // Adding note test
        doMockSignUp(testFirstname, testSurname, testUsername2, testPassword);
        doLogIn(testUsername2, testPassword);

        Assertions.assertEquals(driver.getTitle(), "Home");

        homePageObject = new HomePageObject(driver);

        homePageObject.goToNotesTab();
        homePageObject.submitNote(testNoteTitle, testNoteDescription);

        Assertions.assertEquals(testNoteTitle, homePageObject.getFirstNoteSubmittedTitle());
        Assertions.assertEquals(testNoteDescription, homePageObject.getFirstNoteSubmittedDescription());

        // Editing note test
        homePageObject.editNote(testNoteTitle2, testNoteDescription2);
        Assertions.assertEquals(testNoteTitle2, homePageObject.getFirstNoteSubmittedTitle());
        Assertions.assertEquals(testNoteDescription2, homePageObject.getFirstNoteSubmittedDescription());

        // Deleting note test
        homePageObject.deleteNote();
        Assertions.assertEquals("Home", driver.getTitle());
        try {
            homePageObject.goToNotesTab();
            WebElement noteDeleteButton = driver.findElement(By.id("buttonDeleteNote"));
            Assertions.fail("Unexpected delete button");
        } catch (org.openqa.selenium.NoSuchElementException nsee) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void testAddingEditingDeletingCredentials() throws InterruptedException {
        // Adding note test
        doMockSignUp(testFirstname, testSurname, testUsername3, testPassword);
        doLogIn(testUsername3, testPassword);

        Assertions.assertEquals(driver.getTitle(), "Home");

        homePageObject = new HomePageObject(driver);

        homePageObject.goToCredentialsTab();
        homePageObject.submitCredential(testCredentialUrl, testCredentialUsername, testCredentialPassword);

        Assertions.assertEquals(testCredentialUrl, homePageObject.getFirstCredentialSubmittedUrl());
        Assertions.assertEquals(testCredentialUsername, homePageObject.getFirstCredentialSubmittedUsername());

        // Editing note test
        homePageObject.editCredential(testCredentialUrl2, testCredentialUsername2);
        Assertions.assertEquals(testCredentialUrl2, homePageObject.getFirstCredentialSubmittedUrl());
        Assertions.assertEquals(testCredentialUsername2, homePageObject.getFirstCredentialSubmittedUsername());

        // Deleting note test
        homePageObject.deleteCredential();
        Assertions.assertEquals("Home", driver.getTitle());
        try {
            homePageObject.goToCredentialsTab();
            WebElement credentialDeleteButton = driver.findElement(By.id("buttonDeleteCredential"));
            Assertions.fail("Unexpected delete button");
        } catch (org.openqa.selenium.NoSuchElementException nsee) {
            Assertions.assertTrue(true);
        }
    }
}
