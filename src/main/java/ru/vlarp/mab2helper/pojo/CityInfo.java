package ru.vlarp.mab2helper.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityInfo {
    private Long id;
    private String name;
    private List<String> villages;
    private List<String> workshops;
    private List<GoodsInfo> surplus;
    private List<GoodsInfo> deficit;
}
