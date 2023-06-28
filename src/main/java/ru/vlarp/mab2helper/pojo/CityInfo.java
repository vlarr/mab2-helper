package ru.vlarp.mab2helper.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityInfo {
    private Long id;
    private String name;
    private String villages;
    private String manufactories;
    private String surplus;
    private String deficit;
}