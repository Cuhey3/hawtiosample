package com.heroku.myapp.hawtiosample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.math3.primes.Primes;

public class NumberRangeUtil {

    private final int min;
    private final int max;
    private final int luckyNumber;
    private final int[] oddArray;
    private final int[] evenArray;

    public NumberRangeUtil(int min, int max) throws Exception {
        this.min = min;
        this.max = max;
        oddArray = IntStream.range(min, max + 1).filter((i) -> i % 2 == 1)
                .toArray();
        evenArray = IntStream.range(min, max + 1).filter((i) -> i % 2 == 0)
                .toArray();
        luckyNumber = getRandomNumberInRange();
    }

    public int[] getOddNumbers() {
        return oddArray;
    }

    public int[] getEvenNumbers() {
        return evenArray;
    }

    public String getRange() {
        return min + "to" + max;
    }

    public final int getRandomNumberInRange() throws Exception {
        int randomNumber = (int) (Math.random() * (max - min + 1)) + min;
        if (randomNumber == luckyNumber) {
            throw new Exception();
        }
        return randomNumber;
    }

    public List<Integer> getPrimes(int i) {
        if (i > 1) {
            return Primes.primeFactors(i);
        } else {
            return new ArrayList<>();
        }
    }
}
