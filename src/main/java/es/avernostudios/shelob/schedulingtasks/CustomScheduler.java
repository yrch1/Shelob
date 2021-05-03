package es.avernostudios.shelob.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class CustomScheduler {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private CronConfig cronConfig;

    @Autowired
    private ScheduledTasks myTask;


    public void scheduleAllCrons() {

        cronConfig.getCrontabSchedule().forEach(cron -> taskScheduler.schedule(myTask, new CronTrigger(cron)));
    }
}