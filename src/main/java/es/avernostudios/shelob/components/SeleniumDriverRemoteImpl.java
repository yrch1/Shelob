package es.avernostudios.shelob.components;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Profile("remoteWebDriver")
@Component
@Slf4j
public class SeleniumDriverRemoteImpl implements SeleniumDriverInterface {
    @Value("${es.avernostudios.hubURL}")
    public String hubURL;

    @Override
    public WebDriver getNewDriver() {
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = null;
        try {
            driver = new RemoteWebDriver(new URL(hubURL), options);
            driver.manage()
                    .timeouts()
                    .implicitlyWait(10, TimeUnit.SECONDS);

            LOGGER.info("Connecting to " + hubURL);

        } catch (MalformedURLException e) {
            LOGGER.error("Exception", e);
        }
        return driver;
    }


}
