package com.udacity.jwdnd.course1.cloudstorage.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPageObject {

    @FindBy(id = "inputFirstName")
    private WebElement firstNameInput;

    @FindBy(id = "inputLastName")
    private WebElement lastNameInput;

    @FindBy(id = "inputUsername")
    private WebElement usernameInput;

    @FindBy(id = "inputPassword")
    private WebElement passwordInput;

    @FindBy(id = "buttonSignUp")
    private WebElement submit;

    public SignupPageObject(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    public void signUp(String firstName, String lastName, String username, String password) {
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submit.click();
    }

}
