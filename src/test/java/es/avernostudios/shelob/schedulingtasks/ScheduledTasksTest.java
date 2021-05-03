package es.avernostudios.shelob.schedulingtasks;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"es.avernostudios.crontabSchedule=*/10 * * * * *"})
class ScheduledTasksTest {


    @SpyBean
    private ScheduledTasks myTask;

    @Test
    public void reportCurrentTime() {

        await().atMost(Duration.ofSeconds(25)).untilAsserted(
                () -> verify(myTask, atLeast(2)
                ).run());
    }
}