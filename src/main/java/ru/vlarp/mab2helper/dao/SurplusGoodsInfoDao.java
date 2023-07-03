package ru.vlarp.mab2helper.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.vlarp.mab2helper.dto.SurplusGoodsInfoDto;

import java.util.List;

@Transactional
public interface SurplusGoodsInfoDao extends CrudRepository<SurplusGoodsInfoDto, Long> {
    List<SurplusGoodsInfoDto> findAllByCityName(String cityName);

    void deleteAllByCityName(String cityName);
}
