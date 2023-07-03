package ru.vlarp.mab2helper.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.vlarp.mab2helper.dto.CityInfoDto;

import java.util.Optional;

@Transactional
public interface CityInfoDao extends CrudRepository<CityInfoDto, Long> {

    Optional<CityInfoDto> findByName(String name);

    void deleteAllByName(String cityName);
}
