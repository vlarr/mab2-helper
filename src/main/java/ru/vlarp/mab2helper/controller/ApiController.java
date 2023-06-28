package ru.vlarp.mab2helper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.vlarp.mab2helper.dao.DefaultCityInfoDao;

@Slf4j
@Controller
@RequestMapping("api")
public class ApiController {
    private DefaultCityInfoDao defaultCityInfoDao;

    @GetMapping("upload-all")
    @ResponseBody
    public String uploadAll() {
        log.info("call upload-all");
        defaultCityInfoDao.upload();
        return "upload: ok";
    }

    @Autowired
    public void setDefaultCityInfoDao(DefaultCityInfoDao defaultCityInfoDao) {
        this.defaultCityInfoDao = defaultCityInfoDao;
    }
}
