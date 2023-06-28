package ru.vlarp.mab2helper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vlarp.mab2helper.dao.DefaultCityInfoDao;

@SpringBootApplication
public class Mab2HelperApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(Mab2HelperApplication.class, args);
        DefaultCityInfoDao defaultCityInfoDao = (DefaultCityInfoDao) context.getBean("defaultCityInfoDao");
        defaultCityInfoDao.upload();
    }
}
