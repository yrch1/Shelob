package es.avernostudios.shelob.components;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Profile("localWebDriver")
@Component
@Slf4j
public class SeleniumDriverLocalImpl implements SeleniumDriverInterface {


    @Override
    public WebDriver getNewDriver() {
        return new ChromeDriver();
    }

}
