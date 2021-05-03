package es.avernostudios.shelob.components;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Profile("remoteWebDriver")
@Component
@Slf4j
public class SeleniumDriverRemoteImpl implements SeleniumDriverInterface {
    @Value("${es.avernostudios.hubURL}")
    public String hubURL;
    private RemoteWebDriver driver;

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @PostConstruct
    @Override
    public void init() {

        try {
            ChromeOptions options = new ChromeOptions();
            driver = new RemoteWebDriver(new URL(hubURL), options);
            driver.manage()
                    .timeouts()
                    .implicitlyWait(10, TimeUnit.SECONDS);

            log.info("Connecting to " + hubURL);

        } catch (Exception e) {
            log.error("Exception", e);
        }

    }


}
