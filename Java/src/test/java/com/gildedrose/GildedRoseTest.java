package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {


    /**
     * - The Quality of an item is never negative
     */
    @Test
    protected void testQualityDegradesNeverNegative() throws Throwable {
        int numberOfDays = 30;
        int startingQuality = 1;

        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),new Item("foo", 0, startingQuality), new Item("Aged Brie", 1, 48), new Item("Conjured Mana Cake", 0, startingQuality)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, numberOfDays);

        for (Item item : app.items) {
            assertThat(item.quality, greaterThanOrEqualTo(0));
        }
    }


    /**
     * - Once the sell by date has passed, Quality degrades twice as fast
     */
    @Test
    protected void testQualityDegradesTwiceAsFastAfterSellDateForNormalItem() throws Throwable {
        int numberOfDays = 3;
        int startingQuality = 10;

        Item[] items = new Item[]{new Item("normalItem", 0, startingQuality)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, numberOfDays);
        assertEquals(startingQuality - (2 * numberOfDays), app.items[0].quality);
    }


    /**
     * - Decreases the sellins in every iteration
     */
    @Test
    protected void testSellInDecreases() throws Throwable {
        int initialSellIn = 10;
        int numberOfIterations = 5;
        Item[] items = new Item[]{new Item("test", initialSellIn, 50), new Item("Aged Brie", initialSellIn, 50), new Item("Aged Brie", initialSellIn, 50)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, 5);
        for (Item item : app.items) {
            assertThat(item.sellIn, equalTo(initialSellIn - numberOfIterations));
        }
    }


    //- "Aged Brie" actually increases in Quality the older it gets
    @Test
    protected void testQualityAgedBrieIncreases() throws Throwable {
        int numberOfDays = 3;
        int startingQuality = 10;
        Item[] items = new Item[]{new Item("Aged Brie", 10, startingQuality)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, numberOfDays);
        assertEquals(numberOfDays + startingQuality, app.items[0].quality);
    }



    //- The Quality of an item is never more than 50
    @Test
    protected void testQualityNeverMoreThan50() throws Throwable {
        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49), new Item("Aged Brie", 0, 49), new Item("normal", 10, 49), new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, 10);
        for (Item item : app.items) {
            assertThat(item.quality, lessThanOrEqualTo(50));
        }
    }

    //	- "Sulfuras", being a legendary item, never has to be sold or decreases in Quality
    //Just for clarification, an item can never have its Quality increase above 50, however "Sulfuras" is a
    //legendary item and as such its Quality is 80 and it never alters.
    // This particular case is not well documented, as the "client" who is running happily the code, has another name for this item, which is "Sulfuras, Hand of Ragnaros"
    @Test
    protected void testSulfuras() throws Throwable {
        Item[] items = new Item[]{new Item("Sulfuras, Hand of Ragnaros", 10, 80)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, 5);
        assertThat(app.items[0].quality, equalTo(80));
    }


    //	- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
    //	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    //	Quality drops to 0 after the concert
    @Test
    protected void testBackstagePasses10DaysOrLess() throws Throwable {
        int numberOfDays = 5;
        int numberOfDaysToConcert = 10;
        int startingQuality = 10;

        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", numberOfDaysToConcert, startingQuality)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, numberOfDays);
        int expectedQuality = startingQuality + (2 * numberOfDays);
        assertThat(app.items[0].quality, equalTo(expectedQuality));

        int extraNumberOfDays = 4;
        expectedQuality = startingQuality + (2 * numberOfDays) + (3 * extraNumberOfDays);
        items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", numberOfDaysToConcert, startingQuality)};
        app = new GildedRose(items);

        updateQualityForDays(app, numberOfDays + extraNumberOfDays);
        assertThat(app.items[0].quality, equalTo(expectedQuality));
    }

    //	- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
    //	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    //	Quality drops to 0 after the concert
    @Test
    protected void testBackstagePasses5DaysOrLess() throws Throwable {
        int numberOfDays = 10;
        int numberOfDaysToConcert = 10;
        int startingQuality = 10;

        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", numberOfDaysToConcert, startingQuality)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, numberOfDays);
        int expectedQuality = startingQuality + (2 * 5) + (3 * 5);
        assertThat(app.items[0].quality, equalTo(expectedQuality));
    }


    private void updateQualityForDays(final GildedRose gildedRose, final int numDays) {
        for (int i = 0; i < numDays; i++) {
            gildedRose.updateQuality();
        }
    }


    //	- "Backstage passes", like aged brie, increases in Quality as its SellIn value approaches;
    //	Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
    //	Quality drops to 0 after the concert
    @Test
    protected void testBackstagePassesPassedConcertDay() throws Throwable {
        int numberOfDays = 11;
        int numberOfDaysToConcert = 10;
        int startingQuality = 10;

        Item[] items = new Item[]{new Item("Backstage passes to a TAFKAL80ETC concert", numberOfDaysToConcert, startingQuality)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, numberOfDays);

        assertThat(app.items[0].quality, equalTo(0));
    }


    //	- "Conjured" items degrade in Quality twice as fast as normal items
    @Test
    protected void testConjuredItem() throws Throwable {
        int numberOfDays = 5;
        int sellIn = 10;
        int initialQuality = 30;
        Item[] items = new Item[]{new Item("Conjured Mana Cake", sellIn, initialQuality)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, numberOfDays);
        for (Item item : items) {
            assertThat(item.quality, equalTo(initialQuality-numberOfDays*2));
        }
    }


    //	- "Conjured" items degrade in Quality twice as fast as normal items
    @Test
    protected void testConjuredItemPastSellIn() throws Throwable {
        int numberOfDays = 5;
        int sellIn = 0;
        int initialQuality = 30;
        Item[] items = new Item[]{new Item("Conjured Mana Cake", sellIn, initialQuality)};
        GildedRose app = new GildedRose(items);
        updateQualityForDays(app, numberOfDays);
        for (Item item : items) {
            assertThat(item.quality, equalTo(initialQuality-numberOfDays*4));
        }
    }

    //	- "Conjured" items degrade in Quality twice as fast as normal items
    @Test
    protected void testConjuredItemAfterSellIn() throws Throwable {
        int numberOfDays = 15;
        int sellIn = 20;
        int initialQuality = 40;
        Item[] conjuredItems = new Item[]{new Item("Conjured Mana Cake", sellIn, initialQuality)};
        Item[] normalItems = new Item[]{new Item("normal", sellIn, initialQuality)};
        GildedRose app = new GildedRose(conjuredItems);
        updateQualityForDays(app, numberOfDays);
        app = new GildedRose(normalItems);
        updateQualityForDays(app, numberOfDays);
        int i = 0;
        for (Item normalItem : normalItems) {
            assertThat(initialQuality - conjuredItems[i++].quality, equalTo(2*(initialQuality- normalItem.quality)));
        }
    }

}
