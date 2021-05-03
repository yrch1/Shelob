package es.avernostudios.shelob.schedulingtasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomScheduler {

    private TaskScheduler taskScheduler;
    private CronConfig cronConfig;
    private ScheduledTasks myTask;

    @Autowired
    public CustomScheduler(TaskScheduler taskScheduler, CronConfig cronConfig, ScheduledTasks scheduledTasks) {
        this.myTask = scheduledTasks;
        this.cronConfig = cronConfig;
        this.taskScheduler = taskScheduler;
    }

    public void scheduleAllCrons() {

        cronConfig.getCrontabSchedule().forEach(cron -> {
            log.info("Setting a new cron: {}", cron);
            taskScheduler.schedule(myTask, new CronTrigger(cron));
        });
    }
}