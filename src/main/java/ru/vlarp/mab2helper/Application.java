package ru.vlarp.mab2helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.vlarp.mab2helper.logic.AppLogic;

@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);
        try {
            AppLogic appLogic = (AppLogic) context.getBean("appLogic");
            appLogic.init();
        } catch (Exception ignore) {
            //  ignore
        }
    }
}
