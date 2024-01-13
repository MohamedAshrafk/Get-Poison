package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    // you can configure the  number of containers to any number
    private final static int numberOfContainers = 1500;
    // you can configure the position of the poison (-1 for setting it to a random position)
    private final static int setPoisonPositionTo = -1;

    public static void main(String[] args) {

        Random r = new Random();

        ArrayList<Boolean> containers = new ArrayList<>(Collections.nCopies(numberOfContainers, false));

        int trueIndex;
        if (setPoisonPositionTo == -1){
            trueIndex = r.nextInt(numberOfContainers);
        } else {
            trueIndex = setPoisonPositionTo;
        }
        containers.set(trueIndex, true);
        System.out.println("getArray function: The true index is put in index: " + trueIndex);

        ArrayList<Boolean> positionalArray = getPoisonPosition(containers);
        System.out.println("The position in array form is: " + positionalArray);

        int index = convertBooleanArrayToInteger(positionalArray);
        System.out.println("The index of the poison in the array is: " + index);
    }

    private static int convertBooleanArrayToInteger(List<Boolean> arr) {
        int resultInt = 0;
        for (int i = 0; i < arr.size(); i++) {
            resultInt |= ((arr.get(i) ? 1 : 0) << (arr.size() - i - 1));
        }

        return resultInt;
    }

    private static ArrayList<Boolean> getPoisonPosition(List<Boolean> containers) {

        int neededBitsForSearching = (int) Math.ceil(Math.log(containers.size()) / Math.log(2));
        System.out.println("The needed bits for searching is: " + neededBitsForSearching);
        ArrayList<Boolean> result = new ArrayList<>(Collections.nCopies(neededBitsForSearching, false));

        ArrayList<Boolean> modifiedContainers = new ArrayList<>(Collections.nCopies((int) Math.pow(2, neededBitsForSearching), false));
        for (int i = 0; i < containers.size(); i++) {
            modifiedContainers.set(i, containers.get(i));
        }

        int bitCounter = 0;
        for (int i = 0; i < neededBitsForSearching; i++) {
            boolean bitResult = getPoisonIndexInternal(modifiedContainers, bitCounter);
            result.set(bitCounter, bitResult);
            bitCounter++;
        }

        return result;
    }

    private static boolean getPoisonIndexInternal(List<Boolean> arr, int bitNumber) {

        int numberOfParts = (int) Math.pow(2, (bitNumber + 1));
        int mixingSampleSize = arr.size() / numberOfParts;

        boolean bitValue = false;

        for (int i = 0; i < numberOfParts; i++) {
            if (i % 2 != 0)
                bitValue |= mixContainers(arr, i * (arr.size() / numberOfParts), mixingSampleSize);
        }

        return bitValue;
    }

    private static boolean mixContainers(List<Boolean> arr, int startPosition, int mixSize) {
        boolean mixingValue = false;
        for (int i = 0; i < mixSize; i++) {
            mixingValue |= arr.get(startPosition + i);
        }
        return mixingValue;
    }
}