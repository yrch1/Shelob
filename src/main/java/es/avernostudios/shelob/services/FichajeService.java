package es.avernostudios.shelob.services;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class FichajeService {

    @Value("${es.avernostudios.hubURL}")
    public String hubURL;
    private WebDriver driver;

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

    @PreDestroy
    public void destroy() {
        log.debug("Destroying webdriver");
        if (driver != null) {
            driver.close();
        }
    }

    public void close() {
        log.debug("Closing webdriver");
        driver.close();
        driver.quit();
    }

    public void navigateTo(String url) {
        driver.navigate()
                .to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }

    public WebDriver getDriver() {
        return driver;
    }


    /**
     *
     */
    public void work() {
        init();
        try {
            navigateTo("https://opensource-demo.orangehrmlive.com/");
            getDriver().findElement(By.id("txtUsername")).sendKeys("Admin");
            getDriver().findElement(By.id("txtPassword")).sendKeys("admin123");
            clickElement(getDriver().findElement(By.xpath("//*[@id=\"btnLogin\"]")));

            log.info(getDriver().getCurrentUrl());

            WebElement dashboardDIV = getDriver().findElement(By.xpath("//*[@id=\"content\"]/div/div[1]/h1"));
            if (dashboardDIV != null) {
                log.info(dashboardDIV.getText());
            }

        } catch (Exception e) {
            log.error("Exception", e);
        }
        close();
    }

    public void init(WebDriver driver) {
        try {
            driver.manage()
                    .timeouts()
                    .implicitlyWait(10, TimeUnit.SECONDS);

            log.info("Connecting to " + hubURL);

        } catch (Exception e) {
            log.error("Exception", e);
        }
    }
}
