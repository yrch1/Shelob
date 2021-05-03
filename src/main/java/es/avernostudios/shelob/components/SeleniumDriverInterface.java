package es.avernostudios.shelob.components;

import org.openqa.selenium.WebDriver;

public interface SeleniumDriverInterface {

    WebDriver getDriver();

    void init();

    void close();
}
