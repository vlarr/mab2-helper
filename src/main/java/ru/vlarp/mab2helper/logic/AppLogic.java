package ru.vlarp.mab2helper.logic;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vlarp.mab2helper.domain.ExtendCityInfo;
import ru.vlarp.mab2helper.domain.ExtendGoodsInfo;
import ru.vlarp.mab2helper.mapper.ExtendCityInfoMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AppLogic {
    private CityInfoService cityInfoService;

    public void init() {
        //TODO: init
        log.info("init");
    }

    public List<ExtendCityInfo> sortByName() {
        return cityInfoService.getCityInfoList().stream()
                .map(ExtendCityInfoMapper.INSTANCE::convert)
                .sorted(Comparator.comparing(ExtendCityInfo::getName))
                .toList();
    }

    public List<ExtendCityInfo> sortByLinkedGoods(String selectedCityName) {
        var idToCityInfoMap = cityInfoService.getCityInfoList().stream()
                .map(ExtendCityInfoMapper.INSTANCE::convert)
                .collect(Collectors.toMap(ExtendCityInfo::getName, Function.identity()));

        var selectedCityInfo = idToCityInfoMap.get(selectedCityName);

        Multimap<Integer, String> numToListCityId = ArrayListMultimap.create();

        numToListCityId.put(0, selectedCityName);

        var otherCityNames = new HashSet<>(idToCityInfoMap.keySet());
        otherCityNames.remove(selectedCityName);

        Set<String> selectedSurplusGoods = selectedCityInfo.getSurplus().stream().map(ExtendGoodsInfo::getName).collect(Collectors.toSet());
        Set<String> selectedDeficitGoods = selectedCityInfo.getDeficit().stream().map(ExtendGoodsInfo::getName).collect(Collectors.toSet());

        for (String cityName : otherCityNames) {
            var cityInfo = idToCityInfoMap.get(cityName);

            Set<String> deficitGoods = cityInfo.getDeficit().stream().map(ExtendGoodsInfo::getName).collect(Collectors.toSet());
            Set<String> goods1Set = Sets.intersection(selectedSurplusGoods, deficitGoods);
            boolean isTargetCity = !goods1Set.isEmpty();
            cityInfo.getDeficit().stream().filter(extendGoodsInfo -> goods1Set.contains(extendGoodsInfo.getName())).forEach(extendGoodsInfo -> extendGoodsInfo.setSelected(true));

            Set<String> surplusGoods = cityInfo.getSurplus().stream().map(ExtendGoodsInfo::getName).collect(Collectors.toSet());
            Set<String> goods2Set = Sets.intersection(selectedDeficitGoods, surplusGoods);
            boolean isSourceCity = !goods2Set.isEmpty();
            cityInfo.getSurplus().stream().filter(extendGoodsInfo -> goods2Set.contains(extendGoodsInfo.getName())).forEach(extendGoodsInfo -> extendGoodsInfo.setSelected(true));

            int listNum;
            if (!isSourceCity && isTargetCity) {
                listNum = 1;
            } else if (isSourceCity && isTargetCity) {
                listNum = 2;
            } else if (isSourceCity && !isTargetCity) {
                listNum = 3;
            } else {
                listNum = 4;
            }

            numToListCityId.put(listNum, cityName);
        }

        ArrayList<ExtendCityInfo> resultCityInfoList = new ArrayList<>();
        for (Integer listNum : numToListCityId.keySet().stream().sorted().toList()) {
            resultCityInfoList.addAll(
                    numToListCityId.get(listNum)
                            .stream()
                            .map(idToCityInfoMap::get)
                            .sorted(Comparator.comparing(ExtendCityInfo::getName))
                            .toList()
            );
        }

        return resultCityInfoList;
    }

    @Autowired
    public void setCityInfoService(CityInfoService cityInfoService) {
        this.cityInfoService = cityInfoService;
    }
}
