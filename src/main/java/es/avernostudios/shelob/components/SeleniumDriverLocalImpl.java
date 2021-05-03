package es.avernostudios.shelob.components;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Profile("localWebDriver")
@Component
@Slf4j
public class SeleniumDriverLocalImpl implements SeleniumDriverInterface {

    private WebDriver driver;

    @Override
    public WebDriver getDriver() {
        return this.driver;
    }

    @PostConstruct
    @Override
    public void init() {
        driver = new ChromeDriver();
    }
}
