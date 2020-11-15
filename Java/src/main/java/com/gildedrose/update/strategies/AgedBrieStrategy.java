package com.gildedrose.update.strategies;

import com.gildedrose.Item;

public class AgedBrieStrategy implements UpdateQualityStrategy {

    @Override
    public void updateQuality(Item item) {
        increaseQuality(item);
        decreaseSellIn(item);
    }

    private void increaseQuality(Item item){
        if (item.quality < 50) {
            item.quality = item.quality +1;
        }
    }

    private void decreaseSellIn(Item item){
        item.sellIn = item.sellIn - 1;
    }
}
