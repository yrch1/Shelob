package es.avernostudios.shelob.schedulingtasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class CustomScheduler {

    private TaskScheduler taskScheduler;
    private CronConfig cronConfig;
    private ScheduledTasks myTask;

    @Value("${es.avernostudios.holidays}")
    String holidaysFile;

    @Autowired
    public CustomScheduler(TaskScheduler taskScheduler, CronConfig cronConfig, ScheduledTasks scheduledTasks) {
        this.myTask = scheduledTasks;
        this.cronConfig = cronConfig;
        this.taskScheduler = taskScheduler;
    }

    private void listCurrentHolidays() {
        try {
            Path path = Paths.get(holidaysFile);

            String holidaysJson = Files.readAllLines(path).get(0);

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Current loaded holidays : " + holidaysJson);
            }
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        }
    }

    public void scheduleAllCrons() {

        cronConfig.getCrontabSchedule().forEach(cron -> {
            LOGGER.info("Setting a new cron: {}", cron);
            taskScheduler.schedule(myTask, new CronTrigger(cron));
        });


        listCurrentHolidays();
    }
}