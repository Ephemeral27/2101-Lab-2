package edu.cssd2101.lab02;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogDataTest {

    @Test
    void text() {
    }

    @Test
    void nullId() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(NullPointerException.class, ()->new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870));
    }
    @Test
    void blankId() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("", "anne of green gables", "L.M Montgomery", 5000, 1870));
    }
    @Test
    void onlySpacesId() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("         ", "anne of green gables", "L.M Montgomery", 5000, 1870));
    }
    @Test
    void validTextWithSpacesId() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertDoesNotThrow(()->new CatalogData("     d1   ", "anne of green gables", "L.M Montgomery", 5000, 1870));
    }

    @Test
    void validId() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertDoesNotThrow(()->new CatalogData("d2", "anne of green gables", "L.M Montgomery", 5000, 1870));
    }

    @Test
    void nullTitle() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(NullPointerException.class, ()->new CatalogData("d1", null, "L.M Montgomery", 5000, 1870));
    }
    @Test
    void blankTitle() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("d2", "", "L.M Montgomery", 5000, 1870));
    }
    @Test
    void onlySpacesTitle() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("d3", "      ", "L.M Montgomery", 5000, 1870));
    }
    @Test
    void validTextWithSpacesTitle() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertDoesNotThrow(()->new CatalogData("d4", "      anne of green gables    ", "L.M Montgomery", 5000, 1870));
    }

    @Test
    void validTitle() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertDoesNotThrow(()->new CatalogData("d5", "anne of green gables", "L.M Montgomery", 5000, 1870));
    }

    @Test
    void nullCreator() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(NullPointerException.class, ()->new CatalogData("d1", "anne of green gables", null, 5000, 1870));
    }
    @Test
    void blankCreator() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("d2", "anne of green gables", "", 5000, 1870));
    }
    @Test
    void onlySpacesCreator() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("d3", "anne of green gables", "       ", 5000, 1870));
    }
    @Test
    void validTextWithSpacesCreator() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertDoesNotThrow(()->new CatalogData("d4", "anne of green gables", "    L.M Montgomery         ", 5000, 1870));
    }

    @Test
    void validCreator() {
        //CatalogData data1 = new CatalogData(null, "anne of green gables", "L.M Montgomery", 5000, 1870);
        assertDoesNotThrow(()->new CatalogData("d5", "anne of green gables", "L.M Montgomery", 5000, 1870));
    }

    @Test
    void negativePriceCents() {
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("d3", "anne of green gables", "L.M Montgomery", -1, 1870));
    }

    @Test
    void zeroPriceCents() {
        assertDoesNotThrow(()->new CatalogData("d3", "anne of green gables", "L.M Montgomery", 0, 1870));
    }

    @Test
    void positivePriceCents() {
        assertDoesNotThrow(()->new CatalogData("d3", "anne of green gables", "L.M Montgomery", 1000, 1870));
    }

    @Test
    void lessThan1450year() {
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("d3", "anne of green gables", "L.M Montgomery", 1000, 1449));
    }

    @Test
    void lowerBoundsYear() {
        assertDoesNotThrow(()->new CatalogData("d3", "anne of green gables", "L.M Montgomery", 1000, 1450));
    }

    @Test
    void upperBoundsYear() {
        assertDoesNotThrow(()->new CatalogData("d3", "anne of green gables", "L.M Montgomery", 1000, 2027));
    }

    @Test
    void moreThan2027year() {
        assertThrows(IllegalArgumentException.class, ()->new CatalogData("d3", "anne of green gables", "L.M Montgomery", 1000, 2028));
    }

    //cast list copying
    @Test
     void castListCopyingAddNewActorAfterAssignment() {
        CatalogData data1 = new CatalogData("v1", "anne of green gables", "L.M Montgomery", 5000, 1870);
        List<String> cast = new ArrayList<>(Arrays.asList("actor1", "actor2", "actor3"));
        VideoMaterial vid = new VideoMaterial(data1, 120, Delivery.PHYSICAL,cast );
        cast.add("actor4");
        assertEquals(3, vid.cast().size());
        assertSame("actor1", vid.cast().get(0));
        assertSame("actor2", vid.cast().get(1));
        assertSame("actor3", vid.cast().get(2));
    }
    @Test
    void castListCopyingAddNewActorToCopyCastList() {
        CatalogData data2 = new CatalogData("v1", "anne of green gables", "L.M Montgomery", 5000, 1870);
        List<String> cast2 = new ArrayList<>(Arrays.asList("actor1", "actor2", "actor3"));
        VideoMaterial vid = new VideoMaterial(data2, 120, Delivery.PHYSICAL,cast2);
        cast2.add("actor4");
        assertEquals(3, vid.cast().size());
        assertSame("actor1", vid.cast().get(0));
        assertSame("actor2", vid.cast().get(1));
        assertSame("actor3", vid.cast().get(2));
    }

    @Test
    void sameID(){
        CatalogData data1 = new CatalogData("b1", "anne of green gables", "L.M Montgomery", 5000, 1870);
        CatalogData data2 = new CatalogData("b1", "anne of green gables", "L.M Montgomery", 5000, 1870);
        CatalogData data3 = new CatalogData("b1", "oliver twist", "Charles Dickens", 5000, 1870);

        Material PrintedBook = MaterialFactory.printed(data1, 300);
        //EBook EBook = MaterialFactory.ebook(data1, 1500, 40000).build();

        //assertEquals(PrintedBook,EBook);

    }
}