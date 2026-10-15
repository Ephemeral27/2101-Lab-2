package edu.cssd2101.lab02;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EBookTest {
    private CatalogData data(long cents) {

        return new CatalogData(

                "E", "Title", "Ada", cents, 2026

        );

    }

    // TEST 1: Zero words

    @Test

    void zeroWordsHasZeroReadingTime() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.PDF,

                100, 0, true

        );

        assertEquals(0, ebook.readingTimeMinutes());

        assertEquals(0, ebook.durationMinutes());

    }

    // TEST 2: One word

    @Test

    void oneWordRoundsUpToOneMinute() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.PDF,

                100, 1, true

        );

        assertEquals(1, ebook.readingTimeMinutes());

    }

    // TEST 3: Just below 250 words

    @Test

    void twoHundredFortyNineWordsTakesOneMinute() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.EPUB,

                100, 249, true

        );

        assertEquals(1, ebook.readingTimeMinutes());

    }

    // TEST 4: Exactly 250 words

    @Test

    void exactlyTwoHundredFiftyWordsTakesOneMinute() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.PDF,

                100, 250, true

        );

        assertEquals(1, ebook.readingTimeMinutes());

    }

    // TEST 5: Just above 250 words

    @Test

    void twoHundredFiftyOneWordsTakesTwoMinutes() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.MOBI,

                100, 251, true

        );

        assertEquals(2, ebook.readingTimeMinutes());

    }

    // TEST 6: Maximum integer without overflow

    @Test

    void maximumWordCountDoesNotOverflow() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.PDF,

                Long.MAX_VALUE, Integer.MAX_VALUE, true

        );

        long expected =

                (Integer.MAX_VALUE + 249L) / 250L;

        assertEquals(expected, ebook.readingTimeMinutes());

        assertEquals(expected, ebook.durationMinutes());

        assertEquals(Integer.MAX_VALUE, ebook.wordCount());

        assertEquals(Long.MAX_VALUE, ebook.fileBytes());

    }

    // TEST 7: DRM disabled and 10% promotion

    @Test

    void drmDiscountCombinesWithPromotion() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.PDF,

                100, 250, false

        );

        DiscountStrategy promotion =

                DiscountStrategy.fixed(1000);

        assertEquals(

                7650,

                ebook.priceCents(2026, promotion)

        );

    }

    // TEST 8: 100% promotion

    @Test

    void fullPromotionMakesPriceZero() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.PDF,

                100, 250, false

        );

        DiscountStrategy promotion =

                DiscountStrategy.fixed(10000);

        assertEquals(

                0,

                ebook.priceCents(2026, promotion)

        );

    }

    // TEST 9: DRM enabled means no intrinsic discount

    @Test

    void drmEnabledHasNoIntrinsicDiscount() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.PDF,

                100, 250, true

        );

        assertEquals(

                10000,

                ebook.priceCents(2026, DiscountStrategy.none())

        );

    }

    // TEST 10: DRM disabled gives 15% discount

    @Test

    void drmDisabledAppliesFifteenPercentDiscount() {

        EBook ebook = new EBook(

                data(10000), EBookFormat.EPUB,

                100, 250, false

        );

        assertEquals(

                8500,

                ebook.priceCents(2026, DiscountStrategy.none())

        );

    }

    // TEST 11: Invalid file sizes

    @Test

    void invalidFileSizesAreRejected() {

        assertThrows(

                IllegalArgumentException.class,

                () -> new EBook(

                        data(10000), EBookFormat.PDF,

                        0, 250, true

                )

        );

        assertThrows(

                IllegalArgumentException.class,

                () -> new EBook(

                        data(10000), EBookFormat.PDF,

                        -1, 250, true

                )

        );

    }

    // TEST 12: Invalid word count and null format

    @Test

    void invalidWordCountAndNullFormatAreRejected() {

        assertThrows(

                IllegalArgumentException.class,

                () -> new EBook(

                        data(10000), EBookFormat.PDF,

                        100, -1, true

                )

        );

        assertThrows(

                NullPointerException.class,

                () -> new EBook(

                        data(10000), null,

                        100, 250, true

                )

        );

    }




}