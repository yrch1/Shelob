package es.avernostudios.shelob.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FichajeServiceImplTest {

    @Autowired
    private FichajeService fichajeService;

    @BeforeAll
    public static void setup() {
        System.setProperty(FichajeServiceImpl.WEBDRIVER_CHROME_DRIVER, "/Users/yrch/Documents/programacion/tools/chromedriver");
    }

    @Test
    public void testChrome() {
        assertTrue(fichajeService.work());
    }
}
