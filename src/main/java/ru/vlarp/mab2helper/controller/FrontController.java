package ru.vlarp.mab2helper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vlarp.mab2helper.dao.DefaultCityInfoDao;
import ru.vlarp.mab2helper.pojo.CityInfo;

import java.util.Comparator;

@Controller
public class FrontController {
    private DefaultCityInfoDao defaultCityInfoDao;


    @GetMapping("/")
    public String root() {
        return "redirect:city-info-list";
    }

    @GetMapping("home")
    public String home() {
        return "redirect:city-info-list";
    }

    @GetMapping("city-info-list")
    public String cityInfoListPage(Model model) {
        defaultCityInfoDao.upload();
        var cityInfoList = defaultCityInfoDao.getCityInfoList();
        cityInfoList.sort(Comparator.comparing(CityInfo::getName));
        model.addAttribute("cityInfoList", cityInfoList);
        return "city-info-list";
    }

    @Autowired
    public void setDefaultCityInfoDao(DefaultCityInfoDao defaultCityInfoDao) {
        this.defaultCityInfoDao = defaultCityInfoDao;
    }
}
