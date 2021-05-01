package es.avernostudios.shelob.schedulingtasks;

import es.avernostudios.shelob.services.FichajeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * https://spring.io/guides/gs/scheduling-tasks/
 */
@Component
@Slf4j
public class ScheduledTasks {

    @Value("${crontabSchedule}")
    public String crontabSchedule;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


    @Autowired
    FichajeService fichajeService;

    @Scheduled(cron = "${crontabSchedule}")
    public void reportCurrentTime() throws MalformedURLException {
        log.info("crontabSchedule: '{}' ", crontabSchedule);
        log.info("The time is now {}", dateFormat.format(new Date()));

        fichajeService.work();

    }
}