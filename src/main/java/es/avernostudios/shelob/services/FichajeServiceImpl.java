package es.avernostudios.shelob.services;

import es.avernostudios.shelob.components.SeleniumDriverInterface;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
@Slf4j
public class FichajeServiceImpl implements FichajeService {

    public static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";

    final
    SeleniumDriverInterface seleniumDriverInterface;

    @Value("${es.avernostudios.username}")
    String username;

    @Value("${es.avernostudios.password}")
    String password;

    @Value("${es.avernostudios.url}")
    String url;
    @Value("${es.avernostudios.isSandbox}")
    boolean isSandbox;

    public FichajeServiceImpl(SeleniumDriverInterface seleniumDriverInterface) {
        this.seleniumDriverInterface = seleniumDriverInterface;
    }


    @PreDestroy
    public void destroy() {
        log.debug("Destroying webdriver");
        if (seleniumDriverInterface.getDriver() != null) {
            seleniumDriverInterface.getDriver().quit();
        }
    }

    public void navigateTo(String url) {
        seleniumDriverInterface.getDriver().navigate()
                .to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }


    /**
     *
     */
    @Override
    public boolean work() {


        String usernameXpath = "//*[@id=\"body\"]/div[2]/div/input";
        String salirXpath = "//*[@id=\"body\"]/div[3]";
        String ficharXpath = "//*[@id=\"body\"]/div[1]";
        String passwordXpath = "//*[@id=\"body\"]/div[4]/div/input";
        String entrarXpath = "//*[@id=\"body\"]/div[5]/div";

        init();
        boolean result = false;
        try {
            navigateTo(url);

            WebDriver driver = seleniumDriverInterface.getDriver();
            WebElement usernameWebElement = driver.findElement(By.xpath(usernameXpath));
            usernameWebElement.clear();
            usernameWebElement.sendKeys(username);
            WebElement passwordWebElement = driver.findElement(By.xpath(passwordXpath));
            passwordWebElement.clear();
            passwordWebElement.sendKeys(password);
            clickElement(driver.findElement(By.xpath(entrarXpath)));

            log.info(driver.getCurrentUrl());


            Thread.sleep(5000);
            WebDriverWait wait = new WebDriverWait(driver, 10);

            WebElement salirWebElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(salirXpath)));

            WebElement mainActionWebElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ficharXpath)));

            if (mainActionWebElement != null && salirWebElement != null) {
                log.info("WebElement {} ", mainActionWebElement.getText());


                if (!isSandbox) {
                    mainActionWebElement.click();
                } else {
                    clickElement(salirWebElement);
                }


                result = true;
            }

        } catch (Exception e) {
            log.error("Exception", e);
        }
        close();
        return result;
    }

    private void init() {
        if (seleniumDriverInterface != null) {
            seleniumDriverInterface.init();
        }
    }

    private void close() {
        log.debug("Closing webdriver");
        seleniumDriverInterface.close();
    }
}
