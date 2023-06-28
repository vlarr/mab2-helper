package ru.vlarp.mab2helper.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo {
    private String name;
    private boolean important;

    public String simpleView() {
        return ((important) ? "!" : "") + name;
    }

    @Override
    public String toString() {
        return simpleView();
    }
}
