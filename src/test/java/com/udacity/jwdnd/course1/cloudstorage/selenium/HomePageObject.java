package com.udacity.jwdnd.course1.cloudstorage.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePageObject {

    @FindBy(id = "nav-files-tab")
    private WebElement navTabFiles;

    @FindBy(id = "nav-notes-tab")
    private WebElement navTabNotes;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navTabCredentials;

    @FindBy(id = "buttonLogout")
    private WebElement buttonLogout;

    @FindBy(id = "noteAdd")
    private WebElement buttonAddNote;

    @FindBy(id = "saveNoteModal")
    private WebElement buttonSubmitNote;

    @FindBy(id = "credentialAdd")
    private WebElement buttonAddCredential;

    @FindBy(id = "saveCredentialModal")
    private WebElement buttonSubmitCredential;

    @FindBy(id = "closeNoteModal")
    private WebElement buttonCloseModalNote;

    @FindBy(id = "closeCredentialModal")
    private WebElement buttonCloseModalCredential;

    @FindBy(id = "buttonEditNote")
    private WebElement buttonEditNote;

    @FindBy(id = "buttonEditCredential")
    private WebElement buttonEditCredential;

    @FindBy(id = "buttonDeleteNote")
    private WebElement buttonDeleteNote;

    @FindBy(id = "buttonDeleteCredential")
    private WebElement buttonDeleteCredential;

    @FindBy(id = "noteTitle")
    private WebElement inputNoteTitle;

    @FindBy(id = "noteDescription")
    private WebElement inputNoteDescription;

    @FindBy(id = "credentialUrl")
    private WebElement inputCredentialUrl;

    @FindBy(id = "credentialUsername")
    private WebElement inputCredentialUsername;

    @FindBy(id = "credentialPassword")
    private WebElement inputCredentialPassword;

    private WebDriver webDriver;

    private WebDriverWait webDriverWait;

    public HomePageObject(WebDriver webDriver) {
        this.webDriver = webDriver;
        webDriverWait = new WebDriverWait(this.webDriver, 2);
        PageFactory.initElements(webDriver, this);
    }

    public void goToNotesTab() {
        webDriver.manage().window().maximize();
        navTabNotes.click();
    }

    public void goToCredentialsTab() throws InterruptedException {
        webDriver.manage().window().maximize();
        navTabCredentials.click();
    }

    public void submitNote(String title, String text) throws InterruptedException {
        goToNotesTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonAddNote));

        buttonAddNote.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonSubmitNote));

        inputNoteTitle.sendKeys(title);
        inputNoteDescription.sendKeys(text);

        buttonSubmitNote.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonLogout));

        goToNotesTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonAddNote));
    }

    public void submitCredential(String url, String username, String password) throws InterruptedException {
        goToCredentialsTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonAddCredential));

        buttonAddCredential.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonSubmitCredential));

        inputCredentialUrl.sendKeys(url);
        inputCredentialUsername.sendKeys(username);
        inputCredentialPassword.sendKeys(password);

        buttonSubmitCredential.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonLogout));

        goToCredentialsTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonAddCredential));
    }

    public void editNote(String title, String text) throws InterruptedException {
        goToNotesTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonEditNote));

        buttonEditNote.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonSubmitNote));

        inputNoteTitle.clear();
        inputNoteTitle.sendKeys(title);
        inputNoteDescription.clear();
        inputNoteDescription.sendKeys(text);
        buttonSubmitNote.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonLogout));

        goToNotesTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonAddNote));
    }

    public void editCredential(String url, String username) throws InterruptedException {
        goToCredentialsTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonEditCredential));

        buttonEditCredential.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonSubmitCredential));

        inputCredentialUrl.clear();
        inputCredentialUsername.clear();

        inputCredentialUrl.sendKeys(url);
        inputCredentialUsername.sendKeys(username);

        buttonSubmitCredential.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonLogout));

        goToCredentialsTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonAddCredential));
    }

    public void deleteNote() throws InterruptedException {
        goToNotesTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonAddNote));

        buttonDeleteNote.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonLogout));

        goToNotesTab();
    }

    public void deleteCredential() throws InterruptedException {
        goToCredentialsTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonAddCredential));

        buttonDeleteCredential.click();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonLogout));

        goToCredentialsTab();
    }

    public String getCredentialDecryptedPassword(Integer credentialid) throws InterruptedException {

        goToCredentialsTab();

        webDriverWait.until(ExpectedConditions.visibilityOfAllElements(buttonEditCredential));

        WebElement credentialToView = webDriver.findElement(By.id(credentialid.toString()));

        WebElement credentialEditButton = credentialToView.findElement(By.id("buttonEditCredential"));

        credentialEditButton.click();

        String password = inputCredentialPassword.getAttribute("value");

        buttonCloseModalCredential.click();

        return password;
    }

    public String getFirstNoteSubmittedTitle() {
        return webDriver.findElement(By.id("tableRowNoteTitle")).getText();
    }

    public String getFirstNoteSubmittedDescription() {
        return webDriver.findElement(By.id("tableRowNoteDescription")).getText();
    }

    public String getFirstCredentialSubmittedUrl() {
        return webDriver.findElement(By.id("tableRowCredentialUrl")).getText();
    }

    public String getFirstCredentialSubmittedUsername() {
        return webDriver.findElement(By.id("tableRowCredentialUsername")).getText();
    }

    public void logout() {
        buttonLogout.click();
    }

}
