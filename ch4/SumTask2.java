/**
 * Fork/join parallelism in Java
 * <p>
 * Figure 4.18
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts  - Tenth Edition
 * Copyright John Wiley & Sons - 2018
 */

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumTask2 extends RecursiveTask<Integer> {
    static final int THRESHOLD = 1000;

    private final int begin;
    private final int end;
    private final int[] array;

    public SumTask2(int begin, int end, int[] array) {
        this.begin = begin;
        this.end = end;
        this.array = array;
    }

    protected Integer compute() {
        if (end - begin < THRESHOLD) {
            // conquer stage 
            int sum = 0;
            for (int i = begin; i <= end; i++)
                sum += array[i];

            return sum;
        } else {
            // divide stage 
            int mid = begin + (end - begin) / 2;

            SumTask2 leftTask = new SumTask2(begin, mid, array);
            SumTask2 rightTask = new SumTask2(mid + 1, end, array);

            leftTask.fork();

            return rightTask.compute() + leftTask.join();
        }
    }

    static final int SIZE = 10000;

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        // create SIZE random integers between 0 and 9
        Random random = new Random();
        int[] array = random.ints(0, 9).limit(SIZE).toArray();

        // use fork-join parallelism to sum the array
        SumTask2 task = new SumTask2(0, SIZE - 1, array);

        int sum = pool.invoke(task);

        System.out.println("The sum is " + sum);
    }
}

