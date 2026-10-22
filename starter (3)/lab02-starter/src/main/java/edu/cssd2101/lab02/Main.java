package edu.cssd2101.lab02;

import java.util.List;

/** Reference demonstration of materials, policies, shipping, checkout and recent search stuff */
public final class Main {
    private Main() {}

    /**
     * Prices one catalogue with a 10 percent promotion and then repeats checkout with an alternate
     * lambda policy
     *
     * @param args ignored arguments
     */
    public static void main(String[] args) {
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

        DiscountStrategy promo = DiscountStrategy.fixed(1000);
        MaterialVisitor<Long> shipping = new ShippingVisitor();
        Checkout checkout = new Checkout(promo, shipping);

        for (Material m : catalog.all()) {
            System.out.println(
                    m.id() + " price=" + m.priceCents(2026, promo) + " shipping=" + m.accept(shipping));
        }
        System.out.println("checkout=" + checkout.totalCents(catalog, 2026) + " cents");
        System.out.println(
                "recent=" + catalog.recent(2026, 2).stream().map(Material::id).toList());

        // Alternate extra-discount policy as a lambda: 20 percent off, Media items only.
        Checkout alternate = new Checkout(m -> m instanceof Media ? 2000 : 0, shipping);
        System.out.println("alternate checkout=" + alternate.totalCents(catalog, 2026) + " cents");
    }
}