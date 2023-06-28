package ru.vlarp.mab2helper.logic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vlarp.mab2helper.dao.DefaultCityInfoDao;
import ru.vlarp.mab2helper.pojo.CityInfo;

import java.util.List;

@Slf4j
@Component
public class AppLogic {
    private DefaultCityInfoDao defaultCityInfoDao;

    public List<CityInfo> getCityInfoList() {
        return defaultCityInfoDao.getCityInfoList();
    }

    @Autowired
    public void setDefaultDao(DefaultCityInfoDao defaultCityInfoDao) {
        this.defaultCityInfoDao = defaultCityInfoDao;
    }
}
