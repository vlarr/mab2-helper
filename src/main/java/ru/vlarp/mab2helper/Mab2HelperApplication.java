package ru.vlarp.mab2helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vlarp.mab2helper.logic.AppLogic;

@SpringBootApplication
public class Mab2HelperApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(Mab2HelperApplication.class, args);

        try {
            AppLogic appLogic = (AppLogic) context.getBean("appLogic");
            appLogic.init();
        } catch (Exception ignore) {
            //  ignore
        }
    }
}
