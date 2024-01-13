package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Random r = new Random();

        ArrayList<Boolean> containers = new ArrayList<>(Collections.nCopies(128, false));

        int trueIndex = r.nextInt(100);
        containers.set(trueIndex, true);
        System.out.println("getArray function: The true index is put in index: " + trueIndex);

        System.out.println(getPoisonPosition(containers));
    }

    private static int getPoisonPosition(List<Boolean> arr) {
        ArrayList<Boolean> result = new ArrayList<>(Collections.nCopies(7, false));

        int bitCounter = 0;
        for (double i = arr.size(); i > 1; i /= 2d) {
            boolean bitResult = getPoisonIndexInternal(arr, bitCounter);
            result.set(bitCounter, bitResult);
            System.out.println(bitResult);
            bitCounter++;
        }

        int index = 0;
        for (int i = 0; i < result.size(); i++) {
            index |= ((result.get(i) ? 1 : 0) << (result.size() - i - 1));
        }

        return index;
    }

    private static boolean getPoisonIndexInternal(List<Boolean> arr, int bitNumber) {

        int numberOfParts = (int) Math.pow(2, (bitNumber + 1));
        int numberOfProcessedParts = numberOfParts / 2;

        boolean bitValue = false;

        for (int i = 0; i < numberOfProcessedParts; i++) {
            bitValue |= mixContainers(arr, i * (arr.size() / numberOfProcessedParts), arr.size() / numberOfParts);
        }

        return !bitValue;
    }

    private static boolean mixContainers(List<Boolean> arr, int startPosition, int mixingSize) {
        boolean mixingValue = false;
        for (int i = 0; i < mixingSize; i++) {
            mixingValue |= arr.get(startPosition + i);
        }
        return mixingValue;
    }
}