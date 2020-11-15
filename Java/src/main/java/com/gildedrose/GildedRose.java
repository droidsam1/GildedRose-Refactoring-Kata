package com.gildedrose;

import com.gildedrose.update.strategies.UpdateQualityStrategyFactory;
import com.gildedrose.update.strategies.UpdateQualityStrategy;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateQuality(item);
        }
    }

    private void updateQuality(Item item) {
        getUpdateStrategyForItem(item).updateQuality(item);
    }

    private UpdateQualityStrategy getUpdateStrategyForItem(Item item) {
        return UpdateQualityStrategyFactory.getUpdateQualityStrategy(item);
    }
}