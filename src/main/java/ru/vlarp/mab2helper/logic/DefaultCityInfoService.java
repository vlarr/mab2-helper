package ru.vlarp.mab2helper.logic;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.mab2helper.dao.*;
import ru.vlarp.mab2helper.domain.CityInfoListValidateResult;
import ru.vlarp.mab2helper.dto.*;
import ru.vlarp.mab2helper.mapper.CityInfoMapper;
import ru.vlarp.mab2helper.pojo.CityInfo;
import ru.vlarp.mab2helper.pojo.GoodsInfo;
import ru.vlarp.mab2helper.pojo.RawCityInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
public class DefaultCityInfoService implements CityInfoService {
    private DictCityNameDao dictCityNameDao;
    private DictGoodsNameDao dictGoodsNameDao;

    private CityInfoDao cityInfoDao;
    private VillageInfoDao villageInfoDao;
    private WorkshopInfoDao workshopInfoDao;
    private SurplusGoodsInfoDao surplusGoodsInfoDao;
    private DeficitGoodsInfoDao deficitGoodsInfoDao;

    @Override
    public void initFromRawInfoList(List<RawCityInfo> rawCityInfoList) {
        cityInfoDao.deleteAll();
        villageInfoDao.deleteAll();
        workshopInfoDao.deleteAll();
        surplusGoodsInfoDao.deleteAll();
        deficitGoodsInfoDao.deleteAll();

        for (RawCityInfo rawCityInfo : rawCityInfoList) {
            this.save(rawCityInfo);
        }
    }

    @Override
    public List<CityInfo> getCityInfoList() {
        ArrayList<CityInfo> cityInfoArrayList = new ArrayList<>();
        for (CityInfoDto cityInfoDto : cityInfoDao.findAll()) {
            var dto = this.findCityInfoByName(cityInfoDto.getName());
            dto.ifPresent(cityInfoArrayList::add);
        }
        return cityInfoArrayList;
    }

    @Override
    public Optional<CityInfo> findCityInfoByName(String name) {
        var cityInfoDto = cityInfoDao.findByName(name);

        if (cityInfoDto.isPresent()) {
            CityInfo cityInfo = new CityInfo();
            cityInfo.setName(name);
            cityInfo.setVillages(villageInfoDao.findAllByCityName(name)
                    .stream()
                    .map(VillageInfoDto::getGoodsName)
                    .toList()
            );

            cityInfo.setWorkshops(workshopInfoDao.findAllByCityName(name)
                    .stream()
                    .map(WorkshopInfoDto::getName)
                    .toList()
            );

            cityInfo.setSurplus(surplusGoodsInfoDao.findAllByCityName(name)
                    .stream()
                    .map(dto -> new GoodsInfo(dto.getName(), dto.getImportant()))
                    .toList()
            );

            cityInfo.setDeficit(deficitGoodsInfoDao.findAllByCityName(name)
                    .stream()
                    .map(dto -> new GoodsInfo(dto.getName(), dto.getImportant()))
                    .toList()
            );
            return Optional.of(cityInfo);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(RawCityInfo rawCityInfo) {
        if (rawCityInfo == null || StringUtils.isBlank(rawCityInfo.getName())) {
            throw new IllegalArgumentException();
        }

        String cityName = rawCityInfo.getName();

        cityInfoDao.deleteAllByName(cityName);
        villageInfoDao.deleteAllByCityName(cityName);
        workshopInfoDao.deleteAllByCityName(cityName);
        surplusGoodsInfoDao.deleteAllByCityName(cityName);
        deficitGoodsInfoDao.deleteAllByCityName(cityName);

        cityInfoDao.save(new CityInfoDto(null, rawCityInfo.getName()));
        for (String goodsName : CityInfoMapper.INSTANCE.splitByDefaultSeparator(rawCityInfo.getVillages())) {
            villageInfoDao.save(new VillageInfoDto(null, rawCityInfo.getName(), goodsName));
        }
        for (String workshopName : CityInfoMapper.INSTANCE.splitByDefaultSeparator(rawCityInfo.getWorkshops())) {
            workshopInfoDao.save(new WorkshopInfoDto(null, rawCityInfo.getName(), workshopName));
        }
        for (GoodsInfo goodsInfo : CityInfoMapper.INSTANCE.splitByDefaultSeparatorAndConvertToGoodsInfo(rawCityInfo.getSurplus())) {
            surplusGoodsInfoDao.save(new SurplusGoodsInfoDto(null, rawCityInfo.getName(), goodsInfo.getName(), goodsInfo.isImportant()));
        }
        for (GoodsInfo goodsInfo : CityInfoMapper.INSTANCE.splitByDefaultSeparatorAndConvertToGoodsInfo(rawCityInfo.getDeficit())) {
            deficitGoodsInfoDao.save(new DeficitGoodsInfoDto(null, rawCityInfo.getName(), goodsInfo.getName(), goodsInfo.isImportant()));
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
        for (CityInfo cityInfo : this.getCityInfoList()) {
            if (!dictCityNameDao.findAllNames().contains(cityInfo.getName())) {
                unknownCityNameList.add(cityInfo.getName());
            }
            for (GoodsInfo goodsInfo : cityInfo.getSurplus()) {
                if (!dictGoodsNameDao.findAllNames().contains(goodsInfo.getName())) {
                    unknownGoodsNameList.add(goodsInfo.getName());
                }
            }
            for (GoodsInfo goodsInfo : cityInfo.getDeficit()) {
                if (!dictGoodsNameDao.findAllNames().contains(goodsInfo.getName())) {
                    unknownGoodsNameList.add(goodsInfo.getName());
                }
            }
        }
        result.setUnknownCities(unknownCityNameList);
        result.setUnknownGoods(unknownGoodsNameList);
        return result;
    }

    @Autowired
    public void setDictCityNameDao(DictCityNameDao dictCityNameDao) {
        this.dictCityNameDao = dictCityNameDao;
    }

    @Autowired
    public void setDictGoodsNameDao(DictGoodsNameDao dictGoodsNameDao) {
        this.dictGoodsNameDao = dictGoodsNameDao;
    }

    @Autowired
    public void setCityInfoDao(CityInfoDao cityInfoDao) {
        this.cityInfoDao = cityInfoDao;
    }

    @Autowired
    public void setVillageInfoDao(VillageInfoDao villageInfoDao) {
        this.villageInfoDao = villageInfoDao;
    }

    @Autowired
    public void setWorkshopInfoDao(WorkshopInfoDao workshopInfoDao) {
        this.workshopInfoDao = workshopInfoDao;
    }

    @Autowired
    public void setSurplusGoodsInfoDao(SurplusGoodsInfoDao surplusGoodsInfoDao) {
        this.surplusGoodsInfoDao = surplusGoodsInfoDao;
    }

    @Autowired
    public void setDeficitGoodsInfoDao(DeficitGoodsInfoDao deficitGoodsInfoDao) {
        this.deficitGoodsInfoDao = deficitGoodsInfoDao;
    }
}
