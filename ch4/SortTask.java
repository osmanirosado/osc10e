/**
 * Example from the Oracle documentation
 * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/RecursiveAction.html
 */

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

class SortTask extends RecursiveAction {
    final long[] array;
    final int lo, hi;

    SortTask(long[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    SortTask(long[] array) {
        this(array, 0, array.length);
    }

    static final int THRESHOLD = 1000;

    protected void compute() {
        if (hi - lo < THRESHOLD)
            sortSequentially(lo, hi);
        else {
            int mid = (lo + hi) >>> 1;

            invokeAll(new SortTask(array, lo, mid),
                    new SortTask(array, mid, hi));

            merge(lo, mid, hi);
        }
    }

    void sortSequentially(int lo, int hi) {
        Arrays.sort(array, lo, hi);
    }

    void merge(int lo, int mid, int hi) {
        long[] buf = Arrays.copyOfRange(array, lo, mid);

        for (int i = 0, j = lo, k = mid; i < buf.length; j++)
            array[j] = (k == hi || buf[i] < array[k]) ?
                    buf[i++] : array[k++];
    }
}