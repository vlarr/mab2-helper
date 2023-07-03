package ru.vlarp.mab2helper.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.vlarp.mab2helper.dto.VillageInfoDto;

import java.util.List;

@Transactional
public interface VillageInfoDao extends CrudRepository<VillageInfoDto, Long> {
    List<VillageInfoDto> findAllByCityName(String cityName);

    void deleteAllByCityName(String cityName);
}
