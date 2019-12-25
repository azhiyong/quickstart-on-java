package xyz.mdou.quickstart.sortable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SortableTest {

    private Integer[] array;

    @Before
    public void setup() {
        array = new Integer[]{23, 4, 19, 34, 13, 6, 9};
    }

    @After
    public void tearDown() {
        array = null;
    }

    @Test
    public void testBubbleSort() {
        Sortable sortable = new BubbleSortable(array);
        sortable.print();
        sortable.sort();
        sortable.print();
    }

    @Test
    public void testFastSort() {
        Sortable sortable = new FastSortable(array);
        sortable.print();
        sortable.sort();
        sortable.print();
    }

    @Test
    public void testSelectSort() {
        Sortable sortable = new SelectSortable(array);
        sortable.print();
        sortable.sort();
        sortable.print();
    }

    @Test
    public void testInsertSort() {
        Sortable sortable = new InsertSortable(array);
        sortable.print();
        sortable.sort();
        sortable.print();
    }
}
