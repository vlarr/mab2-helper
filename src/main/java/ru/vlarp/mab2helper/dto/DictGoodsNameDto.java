package ru.vlarp.mab2helper.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vlarp.mab2helper.enums.GoodsTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DICT_GOODS")
public class DictGoodsNameDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;
    private String name;
    @Column(name = "GOODS_TYPE")
    @Enumerated(EnumType.STRING)
    private GoodsTypeEnum goodsType;
}
