package ru.vlarp.mab2helper.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.vlarp.mab2helper.domain.ExtendCityInfo;
import ru.vlarp.mab2helper.pojo.CityInfo;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtendCityInfoMapper {
    public static final ExtendCityInfoMapper INSTANCE = new ExtendCityInfoMapper();

    public ExtendCityInfo convert(CityInfo cityInfo) {
        ExtendCityInfo extendCityInfo = new ExtendCityInfo();
        extendCityInfo.setId(cityInfo.getId());
        extendCityInfo.setName(cityInfo.getName());
        extendCityInfo.setVillages(new ArrayList<>(cityInfo.getVillages()));
        extendCityInfo.setWorkshops(new ArrayList<>(cityInfo.getWorkshops()));
        extendCityInfo.setSurplus(cityInfo.getSurplus().stream().map(ExtendGoodsInfoMapper.INSTANCE::convert).toList());
        extendCityInfo.setDeficit(cityInfo.getDeficit().stream().map(ExtendGoodsInfoMapper.INSTANCE::convert).toList());
        return extendCityInfo;
    }

}
