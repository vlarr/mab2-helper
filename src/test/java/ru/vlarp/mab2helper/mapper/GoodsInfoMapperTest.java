package ru.vlarp.mab2helper.mapper;

import org.junit.jupiter.api.Test;
import ru.vlarp.mab2helper.pojo.GoodsInfo;

import static org.junit.jupiter.api.Assertions.*;

class GoodsInfoMapperTest {

    @Test
    void convert() {
        //given when then
        assertEquals(
                new GoodsInfo("horses", false, ""),
                GoodsInfoMapper.INSTANCE.convert("horses")
        );

        assertEquals(
                new GoodsInfo("horses", true, ""),
                GoodsInfoMapper.INSTANCE.convert("!horses")
        );

        assertEquals(
                new GoodsInfo("horses", false, "500"),
                GoodsInfoMapper.INSTANCE.convert("horses(500)")
        );

        assertEquals(
                new GoodsInfo("horses", true, "600"),
                GoodsInfoMapper.INSTANCE.convert("!horses (600)")
        );

        assertEquals(
                new GoodsInfo("horses", true, "700"),
                GoodsInfoMapper.INSTANCE.convert("(700) horses!")
        );
    }
}