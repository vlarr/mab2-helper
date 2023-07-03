package ru.vlarp.mab2helper.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.vlarp.mab2helper.dto.DeficitGoodsInfoDto;

import java.util.List;

@Transactional
public interface DeficitGoodsInfoDao extends CrudRepository<DeficitGoodsInfoDto, Long> {
    List<DeficitGoodsInfoDto> findAllByCityName(String cityName);

    void deleteAllByCityName(String cityName);
}
