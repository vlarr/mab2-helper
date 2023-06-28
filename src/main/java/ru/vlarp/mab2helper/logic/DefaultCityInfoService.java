package ru.vlarp.mab2helper.logic;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.mab2helper.domain.CityInfoListValidateResult;
import ru.vlarp.mab2helper.mapper.CityInfoMapper;
import ru.vlarp.mab2helper.pojo.CityInfo;
import ru.vlarp.mab2helper.pojo.GoodsInfo;
import ru.vlarp.mab2helper.pojo.RawCityInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Slf4j
@Service
public class DefaultCityInfoService implements CityInfoService {
    private List<CityInfo> cityInfoList = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong(1);

    private DictionaryService dictionaryService;

    @Override
    public void initFromRawInfoList(List<RawCityInfo> rawCityInfoList) {
        var tmpCityInfoList = rawCityInfoList.stream().map(CityInfoMapper.INSTANCE::convert).toList();
        idCounter = new AtomicLong(1);
        tmpCityInfoList.forEach(ci -> ci.setId(idCounter.getAndIncrement()));
        cityInfoList = tmpCityInfoList;
    }

    @Override
    public List<CityInfo> getCityInfoList() {
        return cityInfoList;
    }

    @Override
    public Optional<CityInfo> findCityInfoById(Long id) {
        return cityInfoList.stream().filter(ci -> id.equals(ci.getId())).findAny();
    }

    @Override
    public Optional<CityInfo> findCityInfoByName(String name) {
        return cityInfoList.stream().filter(ci -> name.equals(ci.getName())).findAny();
    }

    @Override
    public void save(CityInfo cityInfo) {
        if (cityInfo == null || StringUtils.isBlank(cityInfo.getName())) {
            throw new IllegalArgumentException();
        }

        if (cityInfo.getId() == null) {
            if (cityInfoList.stream().anyMatch(ci -> ci.getName().equals(cityInfo.getName()))) {
                throw new IllegalArgumentException();
            }
            cityInfo.setId(idCounter.getAndIncrement());
            cityInfoList.add(cityInfo);
        } else {
            CityInfo targetCityInfo = findCityInfoById(cityInfo.getId()).orElseThrow();
            targetCityInfo.setVillages(cityInfo.getVillages());
            targetCityInfo.setWorkshops(cityInfo.getWorkshops());
            targetCityInfo.setSurplus(cityInfo.getSurplus());
            targetCityInfo.setDeficit(cityInfo.getDeficit());
        }
    }

    @Override
    public void validateRawCityInfoList(List<RawCityInfo> rawCityInfoList) throws ValidationException {
        Multiset<String> names = rawCityInfoList.stream().map(RawCityInfo::getName).collect(
                Multisets.toMultiset(Function.identity(), i -> 1, HashMultiset::create)
        );
        if (!names.entrySet().stream().allMatch(stringEntry -> stringEntry.getCount() == 1)) {
            throw new ValidationException("Invalid city info values: non unique entries");
        }
    }

    public CityInfoListValidateResult validateCityInfoAndGetResult() {
        CityInfoListValidateResult result = new CityInfoListValidateResult();
        ArrayList<String> unknownCityNameList = new ArrayList<>();
        ArrayList<String> unknownGoodsNameList = new ArrayList<>();
        for (CityInfo cityInfo : cityInfoList) {
            if (!dictionaryService.getCityNameSet().contains(cityInfo.getName())) {
                unknownCityNameList.add(cityInfo.getName());
            }
            for (GoodsInfo goodsInfo : cityInfo.getSurplus()) {
                if (!dictionaryService.getGoodsNameSet().contains(goodsInfo.getName())) {
                    unknownGoodsNameList.add(goodsInfo.getName());
                }
            }
            for (GoodsInfo goodsInfo : cityInfo.getDeficit()) {
                if (!dictionaryService.getGoodsNameSet().contains(goodsInfo.getName())) {
                    unknownGoodsNameList.add(goodsInfo.getName());
                }
            }
        }
        result.setUnknownCities(unknownCityNameList);
        result.setUnknownGoods(unknownGoodsNameList);
        return result;
    }

    @Autowired
    public void setDictionaryService(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
}
