package com.unrealdinnerbone.lib.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomList<E> {

    private final Map<E, Double> values;
    private final Random random;
    private double total;

    public RandomList() {
        this.values = new HashMap<>();
        this.random = ThreadLocalRandom.current();
        this.total = 0;
    }

    public void add(double weight, E result) {
        this.total += weight;
        this.values.put(result, weight);
    }

    public E next() {
        E result = null;
        double bestValue = Double.MAX_VALUE;
        for (E element : values.keySet()) {
            double value = -Math.log(random.nextDouble()) / values.get(element);
            if (value < bestValue) {
                bestValue = value;
                result = element;
            }
        }
        return result;
    }
    public double getTotal() {
        return total;
    }

    public Map<E, Double> getMap() {
        return values;
    }
}