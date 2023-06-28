package ru.vlarp.mab2helper.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.vlarp.mab2helper.pojo.GoodsInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GoodsInfoMapper {
    public static final GoodsInfoMapper INSTANCE = new GoodsInfoMapper();

    public GoodsInfo convert(String string) {
        GoodsInfo goodsInfo = new GoodsInfo();
        if (string.contains("!")) {
            goodsInfo.setImportant(true);
        }
        goodsInfo.setName(string.replace("!", ""));
        return goodsInfo;
    }
}
