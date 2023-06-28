package ru.vlarp.mab2helper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vlarp.mab2helper.dao.DefaultCityInfoDao;
import ru.vlarp.mab2helper.pojo.CityInfo;

import java.util.Comparator;

@Controller
public class FrontController {
    private DefaultCityInfoDao defaultCityInfoDao;


    @GetMapping("/")
    public String root() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("city-info-list")
    public String cityInfoListPage(Model model) {
        var cityInfoList = defaultCityInfoDao.getCityInfoList();
        cityInfoList.sort(Comparator.comparing(CityInfo::getName));
        var cityNameList = cityInfoList.stream().map(CityInfo::getName).toList();

        model.addAttribute("cityInfoList", cityInfoList);
        model.addAttribute("cityNameList", cityNameList);
        return "city-info-list";
    }

    @GetMapping("city-info-editor")
    public String cityInfoEditorPage(Model model, @RequestParam Long id) {
        CityInfo cityInfo = defaultCityInfoDao.findById(id).orElseThrow();
        model.addAttribute("cityInfo", cityInfo);
        return "city-info-editor";
    }

    @Autowired
    public void setDefaultCityInfoDao(DefaultCityInfoDao defaultCityInfoDao) {
        this.defaultCityInfoDao = defaultCityInfoDao;
    }
}
