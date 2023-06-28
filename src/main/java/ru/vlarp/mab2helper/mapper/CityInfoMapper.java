package ru.vlarp.mab2helper.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.vlarp.mab2helper.pojo.CityInfo;
import ru.vlarp.mab2helper.pojo.GoodsInfo;
import ru.vlarp.mab2helper.pojo.RawCityInfo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CityInfoMapper {
    public static final CityInfoMapper INSTANCE = new CityInfoMapper();

    public static final String REGEX_DEFAULT_SEPARATOR = " *, *";

    public CityInfo convert(RawCityInfo rawCityInfo) {
        CityInfo cityInfo = new CityInfo();
        cityInfo.setName(rawCityInfo.getName());
        cityInfo.setVillages(splitByDefaultSeparator(rawCityInfo.getVillages()));
        cityInfo.setWorkshops(splitByDefaultSeparator(rawCityInfo.getWorkshops()));
        cityInfo.setSurplus(splitByDefaultSeparatorAndConvertToGoodsInfo(rawCityInfo.getSurplus()));
        cityInfo.setDeficit(splitByDefaultSeparatorAndConvertToGoodsInfo(rawCityInfo.getDeficit()));
        return cityInfo;
    }

    public RawCityInfo toRawCityInfo(CityInfo cityInfo) {
        RawCityInfo rawCityInfo = new RawCityInfo();
        rawCityInfo.setName(cityInfo.getName());
        rawCityInfo.setVillages(String.join(", ", cityInfo.getVillages()));
        rawCityInfo.setWorkshops(String.join(", ", cityInfo.getWorkshops()));
        rawCityInfo.setSurplus(cityInfo.getSurplus().stream().map(GoodsInfo::simpleView).collect(Collectors.joining(", ")));
        rawCityInfo.setDeficit(cityInfo.getDeficit().stream().map(GoodsInfo::simpleView).collect(Collectors.joining(", ")));
        return rawCityInfo;
    }

    private List<String> splitByDefaultSeparator(String string) {
        return Arrays.stream(string.split(REGEX_DEFAULT_SEPARATOR))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .toList();
    }

    private List<GoodsInfo> splitByDefaultSeparatorAndConvertToGoodsInfo(String string) {
        return Arrays.stream(string.split(REGEX_DEFAULT_SEPARATOR))
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .map(GoodsInfoMapper.INSTANCE::convert)
                .toList();
    }
}
