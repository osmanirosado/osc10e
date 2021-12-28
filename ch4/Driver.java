/**
 * Summation program using exectuors/callable/futures
 * <p>
 * Figure 4.14
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts  - Tenth Edition
 * Copyright John Wiley & Sons - 2018
 */

import java.util.concurrent.*;

class Summation implements Callable<Integer> {
    private final int upper;

    public Summation(int upper) {
        this.upper = upper;
    }

    public Integer call() {
        int sum = 0;
        for (int i = 1; i <= upper; i++)
            sum += i;
        return sum;
    }
}


public class Driver {
    public static void main(String[] args) {
        int upper = 256;

        ExecutorService pool = Executors.newSingleThreadExecutor();
        Future<Integer> result = pool.submit(new Summation(upper));

        try {
            System.out.println("sum = " + result.get());
        } catch (InterruptedException | ExecutionException ie) {
        }

        pool.shutdown();
    }
}
