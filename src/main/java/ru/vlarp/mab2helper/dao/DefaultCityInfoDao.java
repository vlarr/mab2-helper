package ru.vlarp.mab2helper.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.vlarp.mab2helper.pojo.CityInfo;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

@Slf4j
@Service
public class DefaultCityInfoDao {
    private static final String JSON_DIR = ".";
    private ArrayList<CityInfo> cityInfoList = new ArrayList<>();

    public void upload() {
        readJson();
        fillIds();
    }

    public List<CityInfo> getCityInfoList() {
        return cityInfoList;
    }

    public Optional<CityInfo> findById(Long id) {
        return cityInfoList.stream().filter(ci -> id.equals(ci.getId())).findAny();
    }

    private void readJson() {
        ArrayList<CityInfo> tempCityInfoList;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            tempCityInfoList = objectMapper.readValue(new FileReader(Path.of(JSON_DIR, "city-info-list.json").toFile()), new TypeReference<ArrayList<CityInfo>>() {
            });
        } catch (Exception ignore) {
            throw new RuntimeException();
        }

        validateCityInfoList(tempCityInfoList);
        cityInfoList = tempCityInfoList;
    }

    private void validateCityInfoList(List<CityInfo> cityInfoList) {
        Multiset<String> names = cityInfoList.stream().map(CityInfo::getName).collect(
                Multisets.toMultiset(Function.identity(), i -> 1, HashMultiset::create)
        );
        if (!names.entrySet().stream().allMatch(stringEntry -> stringEntry.getCount() == 1)) {
            throw new RuntimeException("Invalid city info values: non unique entries");
        }
    }

    private void fillIds() {
        AtomicLong id = new AtomicLong(1);
        cityInfoList.forEach(ci -> ci.setId(id.getAndIncrement()));
    }
}
