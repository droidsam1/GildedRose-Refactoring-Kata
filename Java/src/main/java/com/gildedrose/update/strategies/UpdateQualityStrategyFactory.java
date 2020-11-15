package com.gildedrose.update.strategies;

import com.gildedrose.Item;

public class UpdateQualityStrategyFactory {

    public static UpdateQualityStrategy getUpdateQualityStrategy(Item item) {
        switch (getItemType(item)) {
            case AGED_BRIE:
                return new AgedBrieStrategy();
            case BACKSTAGE:
                return new BackStageStrategy();
            case SULFURAS:
                return new SulfurasStrategy();
            case CONJURED:
                return new ConjuredStrategy();
            default:
                return new NormalStrategy();
        }

    }


    public static ItemType getItemType(Item item){
        return ItemType.get(item);
    }

    private enum ItemType {
        NORMAL(""), //
        AGED_BRIE("Aged Brie"), //
        BACKSTAGE("Backstage"),//
        SULFURAS("Sulfuras"),//
        CONJURED("Conjured");

        private String name;

        ItemType(String name) {
            this.name = name;
        }

        public static ItemType get(Item item) {
            for (ItemType type : values()) {
                if (!type.equals(NORMAL) && item.name.toLowerCase().contains(type.name.toLowerCase())) {
                    return type;
                }
            }
            return NORMAL;
        }

    }
}
