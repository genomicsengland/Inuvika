package co.uk.gel.proj.pages;

import co.uk.gel.lib.SeleniumLib;
import co.uk.gel.proj.config.AppConfig;
import co.uk.gel.proj.util.Debugger;
import org.openqa.selenium.WebDriver;

public class AppHomePage {

    private WebDriver driver;
    private SeleniumLib seleniumLib;

    public void testMethod(){
        Debugger.println("User is in the Application Home Page!");
    }

    public AppHomePage(WebDriver driver) {
        this.driver = driver;
        seleniumLib = new SeleniumLib(driver);
    }

    public boolean navigatesToInuvika() {
        String url = AppConfig.getAppURL();
        driver.get(url);
        seleniumLib.pageLoadTime();
        return true;
    }


}//end
