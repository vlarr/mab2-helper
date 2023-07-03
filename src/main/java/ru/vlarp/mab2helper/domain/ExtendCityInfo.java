package ru.vlarp.mab2helper.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendCityInfo {
    private Long id;
    private String name;
    private List<String> villages;
    private List<String> workshops;
    private List<ExtendGoodsInfo> surplus;
    private List<ExtendGoodsInfo> deficit;
    private boolean selected;
}
