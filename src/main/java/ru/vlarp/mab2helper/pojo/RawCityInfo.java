package ru.vlarp.mab2helper.pojo;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RawCityInfo {
    private String name;
    private String villages;
    private String workshops;
    private String surplus;
    private String deficit;
}