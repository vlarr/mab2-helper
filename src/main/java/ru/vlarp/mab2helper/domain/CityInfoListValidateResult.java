package ru.vlarp.mab2helper.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityInfoListValidateResult {
    private Set<String> unknownVillages;
    private Set<String> unknownCities;
    private Set<String> unknownGoods;
}
