package ru.vlarp.mab2helper.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityInfo {
    private Long id;
    private String name;
    private List<String> villages = new ArrayList<>();
    private List<String> workshops = new ArrayList<>();
    private List<GoodsInfo> surplus = new ArrayList<>();
    private List<GoodsInfo> deficit = new ArrayList<>();
}
