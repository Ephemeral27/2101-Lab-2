package edu.cssd2101.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

class T6CheckoutTest {
    private final Checkout checkout = new Checkout(DiscountStrategy.none(), new ShippingVisitor());

    private static EBook ebook(String id, long price) {
        return new EBook(
                new CatalogData(id, "Title", "Creator", price, 2026), EBookFormat.EPUB, 1, 1, true);
    }

    @Test
    void emptyCatalogueQuotesZero() {
        assertEquals(0L, checkout.totalCents(new InMemoryCatalog(), 2026));
    }

    @Test
    void invalidContextFailsEvenWhenCatalogueIsEmpty() {
        MaterialCatalog empty = new InMemoryCatalog();
        assertThrows(IllegalArgumentException.class, () -> checkout.totalCents(empty, 1449));
        assertThrows(IllegalArgumentException.class, () -> checkout.totalCents(empty, 10000));
    }

    @Test
    void repeatedQuotesGiveTheSameTotal() {
        MaterialCatalog catalog = new InMemoryCatalog();
        catalog.add(ebook("E1", 101));
        catalog.add(
                MaterialFactory.printed(
                        new CatalogData("P1", "Java Design", "Ada", 2000, 2022), 501));
        long first = checkout.totalCents(catalog, 2026);
        long second = checkout.totalCents(catalog, 2026);
        assertEquals(first, second);
        assertEquals(1700L + 300L + 101L, first);
    }

    @Test
    void overflowThrowsAndPreservesCatalogueState() {
        MaterialCatalog catalog = new InMemoryCatalog();
        catalog.add(ebook("E1", Long.MAX_VALUE));
        catalog.add(ebook("E2", Long.MAX_VALUE));
        List<Material> before = catalog.all();
        assertThrows(ArithmeticException.class, () -> checkout.totalCents(catalog, 2026));
        assertEquals(before, catalog.all());
        assertEquals(2, catalog.all().size());
    }

    @Test
    void alternatePolicyChangesTheTotal() {
        MaterialCatalog catalog = new InMemoryCatalog();
        catalog.add(ebook("E1", 1000));
        Checkout half = new Checkout(m -> 5000, new ShippingVisitor());
        assertEquals(500L, half.totalCents(catalog, 2026));
        assertEquals(1000L, checkout.totalCents(catalog, 2026));
    }

    @Test
    void fixedStrategyMatchesReferenceTotal() {
        MaterialCatalog catalog = new InMemoryCatalog();
        catalog.add(
                MaterialFactory.printed(
                        new CatalogData("P1", "Java Design", "Ada", 2000, 2022), 501));
        catalog.add(
                MaterialFactory.magazine(
                        new CatalogData("M1", "Object Monthly", "Grace", 800, 2025), 12));
        catalog.add(
                MaterialFactory.audio(
                        new CatalogData("A1", "Audio Patterns", "Linus", 1500, 2026),
                        90,
                        Delivery.PHYSICAL,
                        true));
        catalog.add(
                MaterialFactory.video(
                        new CatalogData("V1", "Design Film", "Barbara", 2500, 2020),
                        120,
                        Delivery.DIGITAL,
                        List.of("Actor One")));
        catalog.add(
                new EBook(
                        new CatalogData("E1", "Short Guide", "Ken", 101, 2026),
                        EBookFormat.EPUB,
                        1_000_000,
                        5000,
                        false));
        Checkout promo = new Checkout(DiscountStrategy.fixed(1000), new ShippingVisitor());
        assertEquals(5487L, promo.totalCents(catalog, 2026));
    }
}