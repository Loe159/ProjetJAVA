package com.monstredepoche.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    private static final Random random = new Random();

    public static boolean tryChance(double chance) {
        return random.nextDouble() < chance;
    }

    public static int getRandomInt(int min, int max) {
        return min + random.nextInt(max - min + 1);
    }

    public static double getRandomDouble(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    public static <T> T getRandomElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(random.nextInt(list.size()));
    }
} 