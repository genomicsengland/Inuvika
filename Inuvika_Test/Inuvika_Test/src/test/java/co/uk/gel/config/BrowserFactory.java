package co.uk.gel.config;

import co.uk.gel.proj.util.Debugger;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class BrowserFactory {
    WebDriver driver;

    public WebDriver getDriver() {
        return getDriver(BrowserConfig.getBrowser(), true);
    }

    public WebDriver getDriver(String browser,
                               boolean javascriptEnabled) {
        BrowserEnum browserEnum = BrowserEnum.valueOf(browser.toUpperCase());
        switch (browserEnum) {
            case CHROME:
                WebDriverManager.chromedriver().clearPreferences();
                WebDriverManager.chromedriver().setup(); // 30-09-2019 - Added WebDriver Manager to get the Chrome Driver version and download
                driver = getChromeDriver(null, javascriptEnabled);
                //driver = getChromeDriver(null, javascriptEnabled);
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().clearPreferences();
                WebDriverManager.firefoxdriver().setup();
                driver = getFirefoxDriver(null, javascriptEnabled);
                break;
            case SAFARI:
                driver = getSafariDriver(null, javascriptEnabled);
                break;
            case IE:
                driver = getInternetExplorer(null, javascriptEnabled);
                break;
            default:
                Debugger.println("Invalid Browser information");
                Assert.assertFalse("Browser : "+browser+" is not present in the BrowserEnum",true);
                break;
        }

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    private WebDriver getSafariDriver(Object object,
                                      boolean javascriptEnabled) {
        DesiredCapabilities safariCaps = DesiredCapabilities.safari();
        safariCaps.setCapability("safari.cleanSession", true);
        return new SafariDriver(safariCaps);
    }

    private WebDriver getFirefoxDriver(String userAgent,
                                       boolean javascriptEnabled) {
        String osName = System.getProperty("os.name");
        String osArchitecture = System.getProperty("os.arch");
        if (osName.toLowerCase().contains("windows")) {
            if (osArchitecture.contains("64")) {
                System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
            } else {
                System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver-v0.23.0-win32.exe");
            }
        } else {
            if (osArchitecture.contains("64")) {
                System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver-v0.23.0-linux64");
            } else {
                System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver-v0.23.0-linux32");
            }
        }
        //FirefoxDriverService ffDriverService = FirefoxDriverService.CreateDefaultService(<driver path>);
        return new FirefoxDriver(getFirefoxOptions(userAgent, javascriptEnabled));
    }

    private FirefoxOptions getFirefoxOptions(String userAgent,
                                             boolean javascriptEnabled) {
        FirefoxProfile profile = new FirefoxProfile();
        profile.setAcceptUntrustedCertificates (true);
//			profile.setEnableNativeEvents(true);
        profile.shouldLoadNoFocusLib();
        profile.setAssumeUntrustedCertificateIssuer(true);
        profile.setPreference("javascript.enabled", javascriptEnabled);
        String downloadFilepath = System.getProperty("user.dir") + File.separator +"downloads"+File.separator;
        try{
            File download_loc = new File(downloadFilepath);
            if(!download_loc.exists()){
                download_loc.mkdirs();
            }
        }catch(Exception exp){
            System.out.println("Exception in creating download directory..."+exp);
        }
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir",downloadFilepath);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/msword, application/json, application/ris, participant_id/csv, image/png, application/pdf, participant_id/html, participant_id/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        if (null != userAgent) {
            profile.setPreference("general.useragent.override", userAgent);
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setProfile(profile);
        firefoxOptions.setCapability("marionette", true);
        return firefoxOptions;
    }

    private WebDriver getChromeDriver(String userAgent,
                                      boolean javascriptEnabled) {
//        System.setProperty("webdriver.chrome.driver",
//                System.getProperty("user.dir") + File.separator + "drivers/chromedriver.exe");
        return new ChromeDriver(getChromeOptions(userAgent, javascriptEnabled));
    }

    private ChromeOptions getChromeOptions(String userAgent,
                                           boolean javascriptEnabled) {
        ChromeOptions opts = new ChromeOptions();
        if (null != userAgent) {
            opts.addArguments("user-agent=" + userAgent);
        }
        if (!javascriptEnabled) {
            opts.addArguments("disable-javascript");
        }
        return opts;
    }

    private WebDriver getInternetExplorer(String UserAgent,
                                          boolean javascriptEnabled) {
        System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + File.separator + "drivers/IEDriverServer.exe");
        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        return driver = new InternetExplorerDriver(capabilities);

    }
}//end


