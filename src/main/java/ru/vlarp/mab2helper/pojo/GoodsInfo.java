package ru.vlarp.mab2helper.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsInfo {
    private String name;
    private boolean important;
    private String price;

    public String simpleView() {
        String importantPrefix = (important) ? "!" : "";
        String priceSuffix = StringUtils.isBlank(price) ? "" : ("(" + price + ")");
        return importantPrefix + name + priceSuffix;
    }

    @Override
    public String toString() {
        return simpleView();
    }
}
