package es.avernostudios.shelob.services;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import es.avernostudios.shelob.Utilities;
import es.avernostudios.shelob.components.SeleniumDriverInterface;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


    @Value("${es.avernostudios.holidays}")
    String holidaysFile;

    public FichajeServiceImpl(SeleniumDriverInterface seleniumDriverInterface) {
        this.seleniumDriverInterface = seleniumDriverInterface;
    }

    public void navigateTo(WebDriver driver, String url) {
        driver.navigate()
                .to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }


    public static int getRandomIntegerBetweenRange(int min, int max) {
        int x = (int) (Math.random() * ((max - min) + 1)) + min;
        return x;
    }

    /**
     *
     */
    @Override
    public boolean work() {

        boolean result = false;
        Date rightNow = Calendar.getInstance().getTime();
        if (!isAHoliday()) {

            addRandomness();

            String usernameXpath = "//*[@id=\"body\"]/div[2]/div/input";
            String salirXpath = "//*[@id=\"body\"]/div[3]";
            String ficharXpath = "//*[@id=\"body\"]/div[1]";
            String passwordXpath = "//*[@id=\"body\"]/div[4]/div/input";
            String entrarXpath = "//*[@id=\"body\"]/div[5]/div";

            String segundoFichar = "//*[@id=\"body\"]/div[3]/div";
            String tercerFichar = "/html/body/div[4]/div[2]/div";

            WebDriver driver = seleniumDriverInterface.getNewDriver();

            try {
                navigateTo(driver, url);

                WebElement usernameWebElement = driver.findElement(By.xpath(usernameXpath));
                usernameWebElement.clear();
                usernameWebElement.sendKeys(username);
                WebElement passwordWebElement = driver.findElement(By.xpath(passwordXpath));
                passwordWebElement.clear();
                passwordWebElement.sendKeys(password);
                clickElement(driver.findElement(By.xpath(entrarXpath)));

                LOGGER.info(driver.getCurrentUrl());


                Thread.sleep(5000);
                WebDriverWait wait = new WebDriverWait(driver, 10);


                WebElement mainActionWebElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ficharXpath)));


                if (mainActionWebElement != null) {
                    LOGGER.info("WebElement {} ", mainActionWebElement.getText());

                    if (!isSandbox) {
                        mainActionWebElement.click();
                        Thread.sleep(2000);

                        WebElement secondActionWebElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(segundoFichar)));
                        secondActionWebElement.click();

                        Thread.sleep(2000);

                        WebElement thirdActionWebElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(tercerFichar)));
                        thirdActionWebElement.click();

                    }


                    Thread.sleep(1000);

                    WebElement salirWebElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(salirXpath)));
                    clickElement(salirWebElement);


                    result = true;
                }

            } catch (Exception e) {
                LOGGER.error("Exception", e);
            } finally {
                driver.quit();
            }
        } else {
            LOGGER.debug(String.format("Today %s is a holiday", rightNow));
            result = true;
        }
        return result;
    }

    private boolean isAHoliday() {
        boolean isTodayAHoliday = false;
        try {
            Path path = Paths.get(holidaysFile);

            String holidaysJson = Files.readAllLines(path).get(0);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Current loaded holidays : " + holidaysJson);
            }

            Type listType = new TypeToken<ArrayList<Date>>() {
            }.getType();
            List<Date> holidayDates = new Gson().fromJson(holidaysJson, listType);


            Date today = Utilities.removeTime(Calendar.getInstance().getTime());
            isTodayAHoliday = holidayDates.contains(today);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
        return isTodayAHoliday;
    }

    private void addRandomness() {
        try {
            int aux = getRandomIntegerBetweenRange(0, 4);
            Thread.sleep(aux * 60 * 1000);
        } catch (InterruptedException e) {
            LOGGER.error("Exception", e);
        }
    }
}
