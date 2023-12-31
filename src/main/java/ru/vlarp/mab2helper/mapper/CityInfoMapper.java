package ru.vlarp.mab2helper.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import ru.vlarp.mab2helper.pojo.CityInfo;
import ru.vlarp.mab2helper.pojo.GoodsInfo;
import ru.vlarp.mab2helper.pojo.RawCityInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CityInfoMapper {
    public static final CityInfoMapper INSTANCE = new CityInfoMapper();

    public static final String REGEX_DEFAULT_SEPARATOR = " *, *";
    public static final String DEFAULT_SEPARATOR = ", ";

    public RawCityInfo toRawCityInfo(CityInfo cityInfo) {
        RawCityInfo rawCityInfo = new RawCityInfo();
        rawCityInfo.setName(cityInfo.getName());
        rawCityInfo.setVillages(String.join(DEFAULT_SEPARATOR, cityInfo.getVillages()));
        rawCityInfo.setWorkshops(String.join(DEFAULT_SEPARATOR, cityInfo.getWorkshops()));
        rawCityInfo.setSurplus(cityInfo.getSurplus().stream().map(GoodsInfo::simpleView)
                .collect(Collectors.joining(DEFAULT_SEPARATOR)));
        rawCityInfo.setDeficit(cityInfo.getDeficit().stream().map(GoodsInfo::simpleView)
                .collect(Collectors.joining(DEFAULT_SEPARATOR)));
        return rawCityInfo;
    }

    public List<String> splitByDefaultSeparator(String string) {
        if (StringUtils.isBlank(string)) {
            return Collections.emptyList();
        } else {
            return Arrays.stream(string.split(REGEX_DEFAULT_SEPARATOR))
                    .map(String::trim)
                    .filter(StringUtils::isNotBlank)
                    .toList();
        }
    }

    public List<GoodsInfo> splitByDefaultSeparatorAndConvertToGoodsInfo(String string) {
        if (StringUtils.isBlank(string)) {
            return Collections.emptyList();
        } else {
            return Arrays.stream(string.split(REGEX_DEFAULT_SEPARATOR))
                    .map(String::trim)
                    .filter(StringUtils::isNotBlank)
                    .map(GoodsInfoMapper.INSTANCE::convert)
                    .toList();
        }
    }
}
