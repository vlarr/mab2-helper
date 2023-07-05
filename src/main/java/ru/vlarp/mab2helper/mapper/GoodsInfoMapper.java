package ru.vlarp.mab2helper.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.vlarp.mab2helper.pojo.GoodsInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsInfoMapper {
    public static final GoodsInfoMapper INSTANCE = new GoodsInfoMapper();

    public GoodsInfo convert(String goodsString) {
        GoodsInfo goodsInfo = new GoodsInfo();
        if (goodsString.contains("!")) {
            goodsInfo.setImportant(true);
        }

        goodsString = goodsString.replace("!", "");
        Pattern pricePattern = Pattern.compile("\\(([\\d,\\-\\s]+)\\)");
        Matcher priceMather = pricePattern.matcher(goodsString);

        String priceStrValue = "";
        if (priceMather.find()) {
            priceStrValue = priceMather.group(1).trim();
            goodsString = priceMather.replaceAll("");
        }
        goodsInfo.setPrice(priceStrValue);
        goodsInfo.setName(goodsString.trim());
        return goodsInfo;
    }
}
