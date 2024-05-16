package vidar.websystem.configuration;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author yishi.xing
 * create datetime 5/16/2024 9:36 AM
 * description
 */
@Configuration
public class TimeZoneConfig {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));//Set to Canada Eastern Time
    }
}
