package es.avernostudios.shelob;

import es.avernostudios.shelob.schedulingtasks.CustomScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
        SpringApplication bootApp = new SpringApplication(ShelobApplication.class);
        ConfigurableApplicationContext context = bootApp.run(args);

        MyBean myBean = context.getBean(MyBean.class);
        myBean.displayAppInfo();


        CustomScheduler scheduledTasks = context.getBean(CustomScheduler.class);

        scheduledTasks.scheduleAllCrons();

    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    private static class MyBean {

        @Value("${application.name}")
        private String applicationName;

        @Value("${build.version}")
        private String buildVersion;

        public void displayAppInfo() {
            StringBuilder myStringBuilder = new StringBuilder();
            myStringBuilder.append("{");
            try {
                myStringBuilder.append("'applicationName':'" + applicationName + "'");
                myStringBuilder.append(",'buildVersion':" + buildVersion);

            } catch (Exception e) {
                LOGGER.error("Exception: ", e);
            }
            myStringBuilder.append("}");
            LOGGER.info(myStringBuilder.toString());
        }
    }

}
