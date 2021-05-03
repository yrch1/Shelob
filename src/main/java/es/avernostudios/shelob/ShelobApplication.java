package es.avernostudios.shelob;

import es.avernostudios.shelob.schedulingtasks.CustomScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;


@SpringBootApplication
@EnableScheduling
@Slf4j
public class ShelobApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ShelobApplication.class, args);

        CustomScheduler scheduledTasks = ctx.getBean(CustomScheduler.class);

        scheduledTasks.scheduleAllCrons();
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

}
