package com.xbenes2.pexeso;
import java.util.Arrays;
import java.util.Random;

public class Utils {
    public static int[] concat(int[] first, int[] second) {
        int firstLength = first.length;
        int secondLength = second.length;

        int[] result = new int[firstLength + secondLength];

        System.arraycopy(first, 0, result, 0, firstLength);
        System.arraycopy(second, 0, result, firstLength, secondLength);

        return result;
    }

    public static int[] shuffle(int[] array){
        int[] result = array.clone();
        Random random = new Random();

        for (int i = 0; i < result.length; i++) {
            int index = random.nextInt(result.length);
            swap(result, i, index);
        }

        return result;
    }

    public static int[] randomSubset(int[] array, int n) {
        int[] shuffled = shuffle(array);
        return Arrays.copyOfRange(shuffled, 0, Math.min(array.length, n));
    }

    public static int getNumberOfFittingSquares(float widthDp, float heightDp, int imageSizeDp) {
        return (int) (Math.floor(heightDp / imageSizeDp) * Math.floor(widthDp / imageSizeDp));
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }
}
