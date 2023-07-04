package ru.vlarp.mab2helper.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.vlarp.mab2helper.dto.CityInfoDto;

import java.util.Optional;
import java.util.Set;

@Transactional
public interface CityInfoDao extends CrudRepository<CityInfoDto, Long> {

    @Query("select name from CityInfoDto ")
    Set<String> findAllNames();

    Optional<CityInfoDto> findByName(String name);

    void deleteAllByName(String cityName);
}
