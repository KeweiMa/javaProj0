package dev.kma.dto;

import lombok.Data;

import java.util.List;

@Data
public class CustomerInfo {
    private String id;

    /**
     * 0: 普客; 1: 稀客; 2: 特殊客人
     */
    private String type;

    private String name;

    /**
     * 持有金额
     */
    private String budget;

    /**
     * 喜好-料理
     */
    private List<String> favTags;

    /**
     * 喜好-酒水
     */
    private List<String> favDrinks;

    /**
     * 厌恶
     */
    private List<String> dislikes;

    /**
     * 推荐料理
     */
    private List<Recipes> recipesList;
}
