package com.xbenes2.pexeso;

import org.junit.Test;

import java.util.Arrays;

import static com.xbenes2.pexeso.Utils.concat;
import static com.xbenes2.pexeso.Utils.getNumberOfFittingSquares;
import static com.xbenes2.pexeso.Utils.randomSubset;
import static com.xbenes2.pexeso.Utils.shuffle;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UtilsTest {
    private static int[] EMPTY_ARRAY = new int[] {};
    @Test
    public void concatenate_joins_two_arrays() {
        int[] first = new int[] { 1, 2 };
        int[] second = new int[] { 3, 4 };
        int[] expected = new int[] { 1, 2, 3, 4 };
        assertArrayEquals(concat(first, second), expected);
    }

    @Test
    public void shuffled_array_should_contain_the_same_elements() {
        int[] array = new int[] { 1, 2, 3, 4 };
        int[] shuffled = shuffle(array);

        Arrays.sort(array);
        Arrays.sort(shuffled);

        assertArrayEquals(array, shuffled);
    }

    @Test
    public void random_subset_should_return_empty_array_for_zero() {
        int[] array = new int[] { 1, 2, 3, 4 };
        int[] result = randomSubset(array, 0);

        assertArrayEquals(result, EMPTY_ARRAY);
    }

    @Test
    public void random_subset_should_return_array_of_given_length() {
        int[] array = new int[] { 1, 2, 3, 4 };
        int length = 2;
        int[] result = randomSubset(array, length);

        assertEquals(result.length, length);
    }

    @Test
    public void random_subset_should_return_the_same_array_if_bigger_subset_requested() {
        int[] array = new int[] { 1, 2, 3, 4 };
        int[] result = randomSubset(array, 5);

        Arrays.sort(array);
        Arrays.sort(result);

        assertArrayEquals(array, result);
    }

    @Test
    public void get_number_of_fitting_images_some_fit() {
        assertEquals(getNumberOfFittingSquares(100, 100, 10), 100);
        assertEquals(getNumberOfFittingSquares(10, 10, 6), 1);
        assertEquals(getNumberOfFittingSquares(10, 5, 4), 2);
    }

    public void get_number_of_fitting_images_returns_zero_if_no_fit() {
        assertEquals(getNumberOfFittingSquares(10, 10, 20), 0);
        assertEquals(getNumberOfFittingSquares(10, 0, 1), 0);
    }
}
