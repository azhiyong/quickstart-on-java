package xyz.mdou.quickstart.sortable;

abstract class AbstractSortable<T> implements Sortable {

    protected T[] array;

    public AbstractSortable(T[] array) {
        this.array = array;
    }

    @Override
    public abstract void sort();

    @Override
    public void print() {
        for (T t : array) {
            System.out.printf("%s \t", t);
        }
        System.out.println();
    }
}