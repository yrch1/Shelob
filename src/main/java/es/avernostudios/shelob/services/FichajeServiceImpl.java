package es.avernostudios.shelob.services;

import es.avernostudios.shelob.components.SeleniumDriverInterface;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

@Service
@Slf4j
public class FichajeServiceImpl implements FichajeService {

    public static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";

    @Autowired
    SeleniumDriverInterface seleniumDriverInterface;

    @Value("${es.avernostudios.username}")
    String username;

    @Value("${es.avernostudios.password}")
    String password;


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
        init();
        boolean result = false;
        try {
            navigateTo("https://webcollab.sourceforge.io/webcollab/index.php");
            WebElement username = seleniumDriverInterface.getDriver().findElement(By.id("username"));
            username.clear();
            username.sendKeys(username.getText());
            WebElement password = seleniumDriverInterface.getDriver().findElement(By.xpath("//*[@id=\"single\"]/div[2]/div/div/form/table/tbody/tr[2]/td[2]/input"));
            password.clear();
            password.sendKeys(password.getText());
            clickElement(seleniumDriverInterface.getDriver().findElement(By.xpath("//*[@id=\"single\"]/div[2]/div/div/form/p/input")));

            log.info(seleniumDriverInterface.getDriver().getCurrentUrl());

            WebElement dashboardDIV = seleniumDriverInterface.getDriver().findElement(By.id("top"));
            if (dashboardDIV != null) {
                log.info(dashboardDIV.getText());
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
        if (seleniumDriverInterface.getDriver() != null) {
            seleniumDriverInterface.getDriver().quit();
        }
    }
}
