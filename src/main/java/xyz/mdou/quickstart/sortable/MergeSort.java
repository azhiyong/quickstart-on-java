package xyz.mdou.quickstart.sortable;

import java.util.Arrays;

/**
 * 归并排序
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] array = {8, 4, 5, 7, 1, 3, 6, 2};
        System.out.println(Arrays.toString(array));
        sort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    public static void sort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(array, left, mid);
            sort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    public static void merge(int[] array, int left, int mid, int right) {
        int i = left;
        int j = mid + 1;
        int tmpIdx = 0;
        int[] tmp = new int[right - left + 1];
        while (i <= mid && j <= right) {
            if (array[i] < array[j]) {
                tmp[tmpIdx++] = array[i++];
            } else {
                tmp[tmpIdx++] = array[j++];
            }
        }
        while (i <= mid) {
            tmp[tmpIdx++] = array[i++];
        }
        while (j <= right) {
            tmp[tmpIdx++] = array[j++];
        }
        tmpIdx = 0;
        while (left <= right) {
            array[left++] = tmp[tmpIdx++];
        }
    }
}
