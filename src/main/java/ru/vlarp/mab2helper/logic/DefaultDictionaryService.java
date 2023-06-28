package ru.vlarp.mab2helper.logic;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DefaultDictionaryService implements DictionaryService {
    private final Set<String> cityNameSet = ImmutableSet.<String>builder()
            .add("Шарас", "Лагета", "Джалмарис", "Порос", "Ликарон")
            .add("Санеопа", "Марунат", "Сеонон", "Дунгланис", "Кар Бансет")
            .add("Ровальт", "Окс Холл", "Правенд", "Пен Кэннок", "Эпикротея")
            .add("Омор", "Бальгард", "Саргот", "Жакулан", "Галенд")
            .build();

    private final Set<String> goodsNameSet = ImmutableSet.<String>builder()
            .add("пиво", "мясо", "сыр", "масло", "древесина")
            .add("финики", "шерсть", "шкуры", "бархат", "инструменты")
            .add("посуда", "зерно", "виноград", "глина", "полотно")
            .add("древесина", "лён", "шелк-сырец", "пиво", "оливки")
            .add("рыба")
            .build();

    @Override
    public Set<String> getCityNameSet() {
        return cityNameSet;
    }

    @Override
    public Set<String> getGoodsNameSet() {
        return goodsNameSet;
    }
}
