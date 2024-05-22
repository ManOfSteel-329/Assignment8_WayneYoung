package com.coderscampus.assignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class A8Solution {
    private static final int THREAD_COUNT = 10;
    private static final ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
    private static final Assignment8 assignment = new Assignment8();
    // number -> the count of how many times this number has appearedcle
    private static final Map<Integer, Integer> numberCountMap = new HashMap<>();

    public static void main(String[] args) {
        CompletableFuture<Void>[] futures = new CompletableFuture[1000];

        for (int i = 0; i < futures.length; i++) {
            final int batchNumber = i;
            futures[i] = CompletableFuture.runAsync(() -> fetchData(batchNumber), executor);

        }

        CompletableFuture.allOf(futures).join();
        numberCountMap.forEach((number, count) -> {
            System.out.print(number + "=" + count);
        });
        System.out.println();

        executor.shutdown();
    }

    private static void fetchData(int batchNumber) {
        // 1. fetch this batch of 1000 numbers via the getNumbers()
        List<Integer> numbersList = assignment.getNumbers();

        // 2. update numberCountMap
        for (Integer n: numbersList) {
            // numberCountMap.merge(n, 1, Integer::sum);
            numberCountMap.put(n, numberCountMap.getOrDefault(n,0) + 1);
        }
    }

}
