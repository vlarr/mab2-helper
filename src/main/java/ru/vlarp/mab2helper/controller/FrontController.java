package ru.vlarp.mab2helper.controller;

import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vlarp.mab2helper.logic.CityInfoService;
import ru.vlarp.mab2helper.logic.AppLogic;
import ru.vlarp.mab2helper.mapper.CityInfoMapper;
import ru.vlarp.mab2helper.pojo.CityInfo;
import ru.vlarp.mab2helper.pojo.RawCityInfo;

import java.io.IOException;

@Controller
public class FrontController {
    private CityInfoService cityInfoService;
    private AppLogic appLogic;

    @GetMapping("/")
    public String root() {
        return "home";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("city-info-list")
    public String cityInfoListPage(Model model, @RequestParam(required = false, value = "selected_city_id") Long selectedCityId) throws IOException, ValidationException {
        if (selectedCityId == null) {
            model.addAttribute("cityInfoList", appLogic.sortByName());
        } else {
            model.addAttribute("cityInfoList", appLogic.sortByLinkedGoods(selectedCityId));
        }
        return "city-info-list";
    }

    @GetMapping("city-info-editor")
    public String cityInfoEditorPage(Model model, @RequestParam Long id) {
        CityInfo cityInfo = cityInfoService.findCityInfoById(id).orElseThrow();
        RawCityInfo rawCityInfo = CityInfoMapper.INSTANCE.toRawCityInfo(cityInfo);
        model.addAttribute("cityId", cityInfo.getId());
        model.addAttribute("rawCityInfo", rawCityInfo);
        return "city-info-editor";
    }

    @GetMapping("city-info-upload-form")
    public String cityInfoUploadForm() {
        return "city-info-upload-form";
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
