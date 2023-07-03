package ru.vlarp.mab2helper.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vlarp.mab2helper.dto.DictCityNameDto;

import java.util.Set;

public interface DictCityNameDao extends CrudRepository<DictCityNameDto, Long> {

    @Query("select name from DictCityNameDto ")
    Set<String> findAllNames();
}
