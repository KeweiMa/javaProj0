package dev.kma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Date: 2023/5//25
 * @Description: 包含 DLC 在内所有食谱，wiki 上扒的
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipes {
    //private ObjecyId id;

    /**
     * wiki id
     */
    private String wikiId;

    private String name;

    /**
     * 制作方式
     */
    private String method;

    /**
     * 售价
     */
    private Integer price;

    /**
     * 食材
     */
    private List<String> ingredients;

    /**
     * 正特性
     */
    private List<String> tags;

    /**
     * 反特性
     */
    private List<String> antiTags;

    /**
     * 烹饪时间
     */
    private Double cookingTime;

    /**
     * 解锁方式
     */
    private String unlockName;

    /**
     * 来源：本体/DLC
     */
    private String source;
}
