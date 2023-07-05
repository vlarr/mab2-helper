package ru.vlarp.mab2helper.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vlarp.mab2helper.dao.CityInfoDao;
import ru.vlarp.mab2helper.dao.DictCityNameDao;
import ru.vlarp.mab2helper.logic.AppLogic;
import ru.vlarp.mab2helper.logic.CityInfoService;
import ru.vlarp.mab2helper.mapper.CityInfoMapper;
import ru.vlarp.mab2helper.pojo.CityInfo;
import ru.vlarp.mab2helper.pojo.RawCityInfo;

import java.util.List;
import java.util.Set;

@Controller
public class FrontController {
    private DictCityNameDao dictCityNameDao;
    private CityInfoDao cityInfoDao;
    private CityInfoService cityInfoService;
    private AppLogic appLogic;

    @GetMapping("/")
    public String root() {
        return "redirect:home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("city-info-list")
    public String cityInfoListPage(Model model, @RequestParam(required = false, value = "selected_city_name") String selectedCityName) {
        if (StringUtils.isBlank(selectedCityName)) {
            model.addAttribute("cityInfoList", appLogic.buildCityInfoListWithSortByName());
        } else {
            model.addAttribute("cityInfoList", appLogic.buildCityInfoListWithSortByLinkedGoods(selectedCityName));
        }
        return "city-info-list";
    }

    @GetMapping("city-info/edit")
    public String cityInfoEdit(Model model, @RequestParam String name) {
        CityInfo cityInfo = cityInfoService.findCityInfoByName(name).orElseThrow();
        RawCityInfo rawCityInfo = CityInfoMapper.INSTANCE.toRawCityInfo(cityInfo);
        model.addAttribute("cityId", cityInfo.getId());
        model.addAttribute("rawCityInfo", rawCityInfo);
        model.addAttribute("isReadonlyCityName", Boolean.TRUE);
        return "city-info-editor";
    }

    @GetMapping("city-info/new")
    public String cityInfoNew(Model model) {
        RawCityInfo rawCityInfo = CityInfoMapper.INSTANCE.toRawCityInfo(new CityInfo());

        Set<String> existsNames = cityInfoDao.findAllNames();
        List<String> allowedCityNames = dictCityNameDao.findAllNames()
                .stream()
                .filter(name -> !existsNames.contains(name))
                .sorted()
                .toList();

        model.addAttribute("rawCityInfo", rawCityInfo);
        model.addAttribute("dictCityNames", allowedCityNames);
        model.addAttribute("isReadonlyCityName", Boolean.FALSE);
        return "city-info-editor";
    }

    @Autowired
    public void setDictCityNameDao(DictCityNameDao dictCityNameDao) {
        this.dictCityNameDao = dictCityNameDao;
    }

    @Autowired
    public void setCityInfoDao(CityInfoDao cityInfoDao) {
        this.cityInfoDao = cityInfoDao;
    }

    @Autowired
    public void setCityInfoService(CityInfoService cityInfoService) {
        this.cityInfoService = cityInfoService;
    }

    @Autowired
    public void setAppLogic(AppLogic appLogic) {
        this.appLogic = appLogic;
    }
}
