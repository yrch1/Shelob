package es.avernostudios.shelob.services;

import org.openqa.selenium.WebElement;

import javax.annotation.PreDestroy;

public interface FichajeService {

    @PreDestroy
    void destroy();

    void close();

    void navigateTo(String url);

    void clickElement(WebElement element);

    boolean work();
}
