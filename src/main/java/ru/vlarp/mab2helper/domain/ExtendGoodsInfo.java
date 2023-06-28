package ru.vlarp.mab2helper.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtendGoodsInfo {
    private String name;
    private boolean important;
    private boolean selected;

    public String simpleView() {
        return ((important) ? "!" : "") + name;
    }

    @Override
    public String toString() {
        return simpleView();
    }
}
