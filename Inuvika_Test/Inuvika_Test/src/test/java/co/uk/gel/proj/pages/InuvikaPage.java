package co.uk.gel.proj.pages;

import co.uk.gel.lib.SeleniumLib;
import co.uk.gel.proj.config.AppConfig;
import co.uk.gel.proj.util.Debugger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InuvikaPage {

    private WebDriver driver;
    private SeleniumLib seleniumLib;
    By username = By.xpath("//input[@id='user_login']");
    By password = By.xpath("//input[@id='user_password']");
    By signInBtn = By.xpath("//input[contains(@id,'connect')]");

    public InuvikaPage(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
    }

    public boolean isCurrentPage() {
        seleniumLib.sleepInSeconds(5);
        By header = By.xpath("//div[@id='loginBoxLogo']");
        seleniumLib.sleepInSeconds(2); // to load inuvika page
        return (seleniumLib.isElementPresent(header));
    }

    public void loginToInuvika() {
        try {
            seleniumLib.sleepInSeconds(2);
            if (isCurrentPage()) {
                seleniumLib.sleepInSeconds(2);
                seleniumLib.sendValue(username, AppConfig.getApp_username());
                seleniumLib.sendValue(password, AppConfig.getApp_password());
                seleniumLib.clickOnElement(signInBtn);
                seleniumLib.sleepInSeconds(10);
            } else {
                Debugger.println("It is NOT navigated to Sign IN of Inuvika.. Directly logged in to Inuvika..");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            Debugger.println("EXCEPTION is Found in loginToInuvika " + exp);
            Assert.assertTrue("EXCEPTION is Found in loginToInuvika", false);
        }
    }

    public void naviagteToDataDiscovery() {
        try {
            seleniumLib.sleepInSeconds(2);
            if (isCurrentPage()) {
                seleniumLib.sleepInSeconds(2);
                seleniumLib.sendValue(username, AppConfig.getApp_username());
                seleniumLib.sendValue(password, AppConfig.getApp_password());
                seleniumLib.clickOnElement(signInBtn);
                seleniumLib.sleepInSeconds(10);
            } else {
                Debugger.println("It is NOT navigated to Sign IN of Inuvika.. Directly logged in to Inuvika..");
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            Debugger.println("EXCEPTION is Found in loginToInuvika " + exp);
            Assert.assertTrue("EXCEPTION is Found in loginToInuvika", false);
        }
    }
}
