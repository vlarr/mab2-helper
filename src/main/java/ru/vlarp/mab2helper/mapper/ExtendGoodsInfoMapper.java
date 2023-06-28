package ru.vlarp.mab2helper.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.vlarp.mab2helper.domain.ExtendGoodsInfo;
import ru.vlarp.mab2helper.pojo.GoodsInfo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtendGoodsInfoMapper {
    public static final ExtendGoodsInfoMapper INSTANCE = new ExtendGoodsInfoMapper();

    public ExtendGoodsInfo convert(GoodsInfo goodsInfo) {
        ExtendGoodsInfo extendGoodsInfo = new ExtendGoodsInfo();
        extendGoodsInfo.setName(goodsInfo.getName());
        extendGoodsInfo.setImportant(goodsInfo.isImportant());
        return extendGoodsInfo;
    }
}
