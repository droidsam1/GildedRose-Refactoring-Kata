package com.gildedrose.update.strategies;

import com.gildedrose.Item;

public class BackStageStrategy implements UpdateQualityStrategy {

    @Override
    public void updateQuality(Item item) {
        if (isBetweenTenDaysAndFiveDaysBeforeConcert(item)) {
            increasesQualityBy(item, 2);
        } else if (isFiveDaysBeforeConcert(item)) {
            increasesQualityBy(item, 3);
        } else {
            increasesQualityBy(item, 1);
        }
        if (isDayOfConcertAlreadyPass(item)) {
            item.quality = 0;
        }
        decreaseSellIn(item);
    }


    private void increasesQualityBy(Item item, int pointsOfQuality){
        item.quality = item.quality + pointsOfQuality;
        if (item.quality >= 50) {
            item.quality = 50;
            return;
        }
    }

    private boolean isBetweenTenDaysAndFiveDaysBeforeConcert(Item item){
        return item.sellIn <= 10 && item.sellIn > 5;
    }

    private boolean isFiveDaysBeforeConcert(Item item){
        return item.sellIn <= 5 && item.sellIn > 0;
    }

    private boolean isDayOfConcertAlreadyPass(Item item){
        return item.sellIn <= 0;
    }

    private void decreaseSellIn(Item item) {
        item.sellIn = item.sellIn - 1;
    }
}
