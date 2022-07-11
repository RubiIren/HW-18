package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.CredentialsConfig;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    @BeforeAll
    public static void configure() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        CredentialsConfig config = ConfigFactory.create(CredentialsConfig.class);//owner

        String loginSelenoid = config.loginSelenoid(),
                passwordSelenoid = config.passwordSelenoid();
        String browser = System.getProperty("browser", "chrome");
        String baseUrl = System.getProperty("baseUrl", "http://demowebshop.tricentis.com");
        String browserSize = System.getProperty("browserSize", "1920x1080");
        String selenoidUrl = System.getProperty("remote", "selenoid.autotests.cloud");

        Configuration.browser = browser;
        Configuration.baseUrl = baseUrl;
        Configuration.browserSize = browserSize;
        Configuration.remote = "https://" + loginSelenoid + ":" + passwordSelenoid + "@" + selenoidUrl + "/wd/hub";


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    void afterEach() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }


}
