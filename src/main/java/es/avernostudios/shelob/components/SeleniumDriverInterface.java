package es.avernostudios.shelob.components;

import org.openqa.selenium.WebDriver;

import javax.annotation.PostConstruct;

public interface SeleniumDriverInterface {

    WebDriver getDriver();

    @PostConstruct
    void init();

}
