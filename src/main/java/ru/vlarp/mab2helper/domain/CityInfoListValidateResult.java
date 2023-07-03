package ru.vlarp.mab2helper.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityInfoListValidateResult {
    private List<String> unknownCities;
    private List<String> unknownGoods;
}
