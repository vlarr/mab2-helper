package ru.vlarp.mab2helper.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.vlarp.mab2helper.dto.WorkshopInfoDto;

import java.util.List;

@Transactional
public interface WorkshopInfoDao extends CrudRepository<WorkshopInfoDto, Long> {
    List<WorkshopInfoDto> findAllByCityName(String cityName);

    void deleteAllByCityName(String cityName);
}
