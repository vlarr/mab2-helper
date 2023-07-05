package ru.vlarp.mab2helper.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vlarp.mab2helper.domain.CityInfoView;
import ru.vlarp.mab2helper.domain.GoodsInfoView;
import ru.vlarp.mab2helper.pojo.CityInfo;

import java.util.ArrayList;
import java.util.Comparator;

@Service
public class CityInfoViewMapper {
    private GoodsInfoViewMapper goodsInfoViewMapper;

    public CityInfoView convert(CityInfo cityInfo) {
        CityInfoView cityInfoView = new CityInfoView();
        cityInfoView.setId(cityInfo.getId());
        cityInfoView.setName(cityInfo.getName());
        cityInfoView.setVillages(new ArrayList<>(cityInfo.getVillages()));
        cityInfoView.setWorkshops(new ArrayList<>(cityInfo.getWorkshops()));
        cityInfoView.setSurplus(cityInfo.getSurplus()
                .stream()
                .map(goodsInfoViewMapper::convert)
                .sorted(new GoodsInfoViewComparator())
                .toList());
        cityInfoView.setDeficit(cityInfo.getDeficit()
                .stream()
                .map(goodsInfoViewMapper::convert)
                .sorted(new GoodsInfoViewComparator())
                .toList());
        return cityInfoView;
    }

    static class GoodsInfoViewComparator implements Comparator<GoodsInfoView> {
        @Override
        public int compare(GoodsInfoView o1, GoodsInfoView o2) {
            long o1num = o1.getOrderNum() == null ? Long.MAX_VALUE : o1.getOrderNum();
            long o2num = o2.getOrderNum() == null ? Long.MAX_VALUE : o2.getOrderNum();
            int order = Long.compare(o1num, o2num);
            if (order == 0) {
                return StringUtils.compare(o1.getName(), o2.getName());
            } else {
                return order;
            }
        }
    }

    @Autowired
    public void setGoodsInfoViewMapper(GoodsInfoViewMapper goodsInfoViewMapper) {
        this.goodsInfoViewMapper = goodsInfoViewMapper;
    }
}
