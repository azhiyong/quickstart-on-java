package xyz.mdou.quickstart.sortable;

public class FastSortable extends AbstractSortable<Integer> {

    public FastSortable(Integer[] array) {
        super(array);
    }

    @Override
    public void sort() {
        sort(array, 0, array.length - 1);
    }

    public void sort(Integer[] array, int low, int high) {
        if (array.length - 1 < high || low < 0 || low > high) {
            return;
        }
        int l = low;
        int h = high;
        int tmp = array[l];
        while (l < h) {
            while (array[h] > tmp && h > l) {
                h--;
            }
            array[l] = array[h];
            while (array[l] < tmp && h > l) {
                l++;
            }
            array[h] = array[l];
        }
        array[l] = tmp;
        sort(array, low, l - 1);
        sort(array, h + 1, high);
    }
}
