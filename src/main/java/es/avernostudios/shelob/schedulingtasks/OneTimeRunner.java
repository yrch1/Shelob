package es.avernostudios.shelob.schedulingtasks;

import es.avernostudios.shelob.components.SeleniumDriverInterface;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OneTimeRunner implements CommandLineRunner {

    public static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";

    @Autowired
    SeleniumDriverInterface seleniumDriverInterface;

    @Override
    public void run(String... args) throws Exception {

        LOGGER.info("Executing OneTimeRunner...");

        WebDriver driver = seleniumDriverInterface.getNewDriver();

        try {
            driver.navigate().to("https://www.google.es");
            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/img"));
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }

    }
}
