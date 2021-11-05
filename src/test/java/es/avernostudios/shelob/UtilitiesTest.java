package es.avernostudios.shelob;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UtilitiesTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void removeTime() {

        Calendar rightNow = Calendar.getInstance();
        rightNow.set(Calendar.HOUR, 0);
        rightNow.set(Calendar.MINUTE, 0);
        rightNow.set(Calendar.SECOND, 0);
        rightNow.set(Calendar.MILLISECOND, 0);
        Date actual = Utilities.removeTime(Calendar.getInstance().getTime());
        assertEquals(rightNow.getTime(), Utilities.removeTime(actual));


        rightNow.set(Calendar.MILLISECOND, 1);
        assertNotEquals(rightNow.getTime(), Utilities.removeTime(actual));
    }
}