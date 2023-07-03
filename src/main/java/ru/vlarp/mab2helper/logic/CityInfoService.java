package ru.vlarp.mab2helper.logic;

import jakarta.xml.bind.ValidationException;
import ru.vlarp.mab2helper.domain.CityInfoListValidateResult;
import ru.vlarp.mab2helper.pojo.CityInfo;
import ru.vlarp.mab2helper.pojo.RawCityInfo;

import java.util.List;
import java.util.Optional;

public interface CityInfoService {
    void initFromRawInfoList(List<RawCityInfo> rawCityInfoList);

    List<CityInfo> getCityInfoList();

    void validateRawCityInfoList(List<RawCityInfo> rawCityInfoList) throws ValidationException;

    CityInfoListValidateResult validateCityInfoAndGetResult();

    Optional<CityInfo> findCityInfoByName(String name);

    void save(RawCityInfo rawCityInfo);
}
