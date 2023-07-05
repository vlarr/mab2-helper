package ru.vlarp.mab2helper.logic;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.vlarp.mab2helper.domain.CityInfoView;
import ru.vlarp.mab2helper.domain.GoodsInfoView;
import ru.vlarp.mab2helper.mapper.CityInfoViewMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AppLogic {
    private CityInfoService cityInfoService;
    private CityInfoViewMapper cityInfoViewMapper;

    public List<CityInfoView> buildCityInfoListWithSortByName() {
        return cityInfoService.getCityInfoList().stream()
                .map(cityInfoViewMapper::convert)
                .sorted(Comparator.comparing(CityInfoView::getName))
                .toList();
    }

    public List<CityInfoView> buildCityInfoListWithSortByLinkedGoods(String selectedCityName) {
        Map<String, CityInfoView> idToCityInfoMap = cityInfoService.getCityInfoList().stream()
                .map(cityInfoViewMapper::convert)
                .collect(Collectors.toMap(CityInfoView::getName, Function.identity()));

        var selectedCityInfo = idToCityInfoMap.get(selectedCityName);

        //  Группы названий городов по порярядку их отображения в списке. Ключ - номер группы:
        //  0 - выбранный город;
        //  1 - города где есть дефицит товаров которые можно приобрести в текущем городе;
        //  2 - города где есть дефицит товаров которые можно приобрести в текущем городе и где есть избыток товаров которые можно сбыть в выбранном городе;
        //  3 - города где есть избыток товаров которые можно сбыть в выбранном городе;
        //  4 - прочие города
        Multimap<Integer, String> numToListCityId = ArrayListMultimap.create();

        numToListCityId.put(0, selectedCityName);

        var otherCityNames = new HashSet<>(idToCityInfoMap.keySet());
        otherCityNames.remove(selectedCityName);

        Set<String> selectedSurplusGoods = selectedCityInfo.getSurplus().stream()
                .map(GoodsInfoView::getName).collect(Collectors.toSet());
        Set<String> selectedDeficitGoods = selectedCityInfo.getDeficit().stream()
                .map(GoodsInfoView::getName).collect(Collectors.toSet());

        for (String cityName : otherCityNames) {
            var cityInfo = idToCityInfoMap.get(cityName);

            Set<String> deficitGoods = cityInfo.getDeficit().stream()
                    .map(GoodsInfoView::getName).collect(Collectors.toSet());
            Set<String> goods1Set = Sets.intersection(selectedSurplusGoods, deficitGoods);
            boolean isTargetCity = !goods1Set.isEmpty();
            cityInfo.getDeficit().stream()
                    .filter(goodsInfoView -> goods1Set.contains(goodsInfoView.getName()))
                    .forEach(goodsInfoView -> goodsInfoView.setSelected(true));

            Set<String> surplusGoods = cityInfo.getSurplus().stream()
                    .map(GoodsInfoView::getName).collect(Collectors.toSet());
            Set<String> goods2Set = Sets.intersection(selectedDeficitGoods, surplusGoods);
            boolean isSourceCity = !goods2Set.isEmpty();
            cityInfo.getSurplus().stream()
                    .filter(goodsInfoView -> goods2Set.contains(goodsInfoView.getName()))
                    .forEach(goodsInfoView -> goodsInfoView.setSelected(true));

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

        ArrayList<CityInfoView> resultCityInfoList = new ArrayList<>();
        for (Integer listNum : numToListCityId.keySet().stream().sorted().toList()) {
            resultCityInfoList.addAll(numToListCityId.get(listNum)
                    .stream()
                    .map(idToCityInfoMap::get)
                    .sorted(Comparator.comparing(CityInfoView::getName))
                    .toList()
            );
        }

        return resultCityInfoList;
    }

    @Autowired
    public void setCityInfoService(CityInfoService cityInfoService) {
        this.cityInfoService = cityInfoService;
    }

    @Autowired
    public void setCityInfoViewMapper(CityInfoViewMapper cityInfoViewMapper) {
        this.cityInfoViewMapper = cityInfoViewMapper;
    }
}
