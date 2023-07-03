package ru.vlarp.mab2helper.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.vlarp.mab2helper.dto.DictGoodsNameDto;

import java.util.Set;

public interface DictGoodsNameDao extends CrudRepository<DictGoodsNameDto, Long> {

    @Query("select name from DictGoodsNameDto ")
    Set<String> findAllNames();
}
