package co.uk.gel.proj.pages;

import co.uk.gel.config.SeleniumDriver;
import co.uk.gel.lib.SeleniumLib;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Pages {

    protected WebDriver driver;
    private SeleniumLib seleniumLib;

    //We have to initialize all the Pages Created in this class. AppHomePage provided as an example.
    protected AppHomePage appHomePage;
    protected InuvikaPage inuvikaPage;

    public Pages(SeleniumDriver driver) {
        this.driver = driver;
        PageObjects();
    }

    public void PageObjects() {

        appHomePage = PageFactory.initElements(driver, AppHomePage.class);
        inuvikaPage = PageFactory.initElements(driver, InuvikaPage.class);
    }
}//end class
