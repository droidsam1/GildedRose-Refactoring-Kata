package com.gildedrose.update.strategies;

import com.gildedrose.Item;

public class NormalStrategy implements UpdateQualityStrategy {

    @Override
    public void updateQuality(Item item) {
        if (item.sellIn <= 0) {
            decreaseQuality(item, 2);
        } else {
            decreaseQuality(item, 1);
        }
        decreaseSellIn(item);
    }


    private void decreaseQuality(Item item, int pointsOfQuality) {
        item.quality = item.quality - pointsOfQuality;
        if (item.quality <= 0) {
            item.quality = 0;
        }
    }

    private void decreaseSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }

}
