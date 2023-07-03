package ru.vlarp.mab2helper.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.vlarp.mab2helper.domain.CityInfoListValidateResult;
import ru.vlarp.mab2helper.logic.CityInfoService;
import ru.vlarp.mab2helper.mapper.CityInfoMapper;
import ru.vlarp.mab2helper.pojo.RawCityInfo;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("api")
public class ApiController {
    private CityInfoService cityInfoService;

    @PostMapping("city-info-list/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            ArrayList<RawCityInfo> tempRawCityInfoList;
            ObjectMapper objectMapper = new ObjectMapper();
            tempRawCityInfoList = objectMapper.readValue(file.getInputStream(), new TypeReference<>() {
            });
            cityInfoService.validateRawCityInfoList(tempRawCityInfoList);
            cityInfoService.initFromRawInfoList(tempRawCityInfoList);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            redirectAttributes.addFlashAttribute("message_load_from_json", "Error upload " + file.getOriginalFilename() + "!");
            return "redirect:/home";
        }

        redirectAttributes.addFlashAttribute("message_load_from_json", "You successfully uploaded " + file.getOriginalFilename() + "!");
        return "redirect:/home";
    }

    @GetMapping("city-info-list/download")
    @ResponseBody
    public ResponseEntity<List<RawCityInfo>> downloadAsFile() {
        List<RawCityInfo> rawCityInfoList = cityInfoService.getCityInfoList().stream()
                .map(CityInfoMapper.INSTANCE::toRawCityInfo)
                .toList();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(rawCityInfoList);
    }

    @PostMapping(value = "city-info", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitCityInfo(@RequestParam MultiValueMap<String, String> paramMap) {
        log.info("call /api/city-info with {}", paramMap);
        RawCityInfo rawCityInfo = new RawCityInfo(
                paramMap.getFirst("name"),
                paramMap.getFirst("villages"),
                paramMap.getFirst("workshops"),
                paramMap.getFirst("surplus"),
                paramMap.getFirst("deficit")
        );
        cityInfoService.save(rawCityInfo);
        return "redirect:/city-info-list";
    }

    @GetMapping("city-info-list/validate")
    @ResponseBody
    public ResponseEntity<CityInfoListValidateResult> validateCityInfoList() {
        return ResponseEntity.ok(cityInfoService.validateCityInfoAndGetResult());
    }


    @Autowired
    public void setCityInfoService(CityInfoService cityInfoService) {
        this.cityInfoService = cityInfoService;
    }
}
