package es.avernostudios.shelob.schedulingtasks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "es.avernostudios")
public class CronConfig {

    private List<String> crontabSchedule;

    @Bean
    public List<String> crontabSchedule() {
        return this.crontabSchedule;
    }

    public List<String> getCrontabSchedule() {
        return crontabSchedule;
    }

    public void setCrontabSchedule(List<String> crontabSchedule) {
        this.crontabSchedule = crontabSchedule;
    }
}