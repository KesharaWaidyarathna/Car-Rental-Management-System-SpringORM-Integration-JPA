package lk.ijse.dep.MostWantedCabs;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(JPAConfig.class)
@Configuration
@ComponentScan("lk.ijse.dep.MostWantedCabs")
public class AppConfig {
}
