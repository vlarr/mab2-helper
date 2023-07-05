package ru.vlarp.mab2helper.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.mab2helper.dao.DictGoodsNameDao;
import ru.vlarp.mab2helper.domain.GoodsInfoView;
import ru.vlarp.mab2helper.dto.DictGoodsNameDto;
import ru.vlarp.mab2helper.pojo.GoodsInfo;

import java.util.Optional;

@Service
public class GoodsInfoViewMapper {
    private DictGoodsNameDao dictGoodsNameDao;

    public GoodsInfoView convert(GoodsInfo goodsInfo) {
        GoodsInfoView goodsInfoView = new GoodsInfoView();
        goodsInfoView.setName(goodsInfo.getName());
        goodsInfoView.setImportant(goodsInfo.isImportant());
        goodsInfoView.setPrice(goodsInfo.getPrice());

        goodsInfoView.setOrderNum(getOrderNum(goodsInfo.getName()).orElse(null));
        return goodsInfoView;
    }

    private Optional<Long> getOrderNum(String name) {
        return dictGoodsNameDao.findByName(name).map(DictGoodsNameDto::getId);
    }

    @Autowired
    public void setDictGoodsNameDao(DictGoodsNameDao dictGoodsNameDao) {
        this.dictGoodsNameDao = dictGoodsNameDao;
    }
}
