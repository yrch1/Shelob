package es.avernostudios.shelob.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
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

        cronConfig.getCrontabSchedule().forEach(cron -> taskScheduler.schedule(myTask, new CronTrigger(cron)));
    }
}