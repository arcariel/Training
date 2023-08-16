import java.util.*;

public class MergeIntervals {

    static Random random = new Random();
    public static void main(String[] args) {
        List<Integer[]> originalInterval = generateIntervals(5);
        Integer[] intervalToMerge = generateIntervals(1).get(0);

        System.out.println("---------------");
        System.out.print("Original intervals: ");
        originalInterval.forEach(integers -> printIntervals(integers));
        System.out.println();
        System.out.println("---------------");
        System.out.print("Interval to merge: ");
        printIntervals(intervalToMerge);
        System.out.println();
        System.out.println("---------------");
        System.out.println("---------------");


        MergeIntervalUtil mergeIntervalUtil = new MergeIntervalUtil();
        List<Integer[]> mergedInterval = mergeIntervalUtil.mergeIntervals(originalInterval, intervalToMerge);

        System.out.print("Merged intervals: ");
        mergedInterval.forEach(intervals -> printIntervals(intervals));
    }

    private static void printIntervals(Integer[] interval) {
        System.out.print(Arrays.toString(interval) + ",");
    }

    private static List<Integer[]> generateIntervals(int maxElements) {
        List<Integer[]> interval = new ArrayList<>();
        Integer[] integerElements = new Integer[maxElements*2];
        for (int i = 0; i < integerElements.length; i++) {
            integerElements[i] = generateIntegerValue(integerElements, randomValue());
        }
        Arrays.sort(integerElements);
        List<Integer> listElements = new LinkedList<>(Arrays.asList(integerElements));
        for (int i = 0; i < maxElements; i++) {
            interval.add(generateIntervalValue(listElements));
        }
        return interval;
    }

    private static Integer generateIntegerValue(Integer[] elements, Integer newValue) {
        if (Arrays.asList(elements).contains(newValue))
            return generateIntegerValue(elements, randomValue());
        return newValue;
    }

    private static Integer[] generateIntervalValue(List<Integer> integerElements) {
        Integer[] interval = new Integer[2];
        for (int i = 0; i < 2; i++) {
            Integer value = integerElements.remove(0);
            interval[i] = value;
        }
        return interval;
    }

    private static int randomValue() {
        return 1 + (int) (random.nextFloat() * (20 - 1 + 1));
    }

}

/**
 * *************************
 * *************************
 * Algorith logic start here
 * *************************
 * *************************
 */
class MergeIntervalUtil {

    public List<Integer[]> mergeIntervals(List<Integer[]> originalInterval, Integer[] intervalToMerge) {
        //find min interval
        Integer[] initialInterval = findInterval(originalInterval, intervalToMerge[0]);
        int minIndex = findIndex(originalInterval, initialInterval);

        //find max interval
        Integer[] finalInterval = findInterval(originalInterval, intervalToMerge[1]);
        int maxIndex = findIndex(originalInterval, finalInterval);

        //generate new Interval values to merge
        return merge(originalInterval, intervalToMerge, minIndex, maxIndex);
    }

    private List<Integer[]> merge(List<Integer[]> originalInterval, Integer[] intervalToMerge, int minIndex, int maxIndex) {
        List<Integer> values = new LinkedList<>();
        values.addAll(Arrays.asList(intervalToMerge));
        values.addAll(Arrays.asList(originalInterval.get(minIndex)));
        values.addAll(Arrays.asList(originalInterval.get(maxIndex)));
        Integer[] merged = new Integer[]{Collections.min(values), Collections.max(values)};
        for (int i = minIndex; i <= maxIndex; i++) {
            originalInterval.remove(minIndex);
        }
        originalInterval.add(merged);

        Collections.sort(originalInterval, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] first, Integer[] second) {
                return first[1].compareTo(second[1]);
            }
        });
        return originalInterval;
    }

    private Integer[] findInterval(List<Integer[]> originalInterval, Integer valueToCompare) {
        List<Integer> defaultValue = new ArrayList<Integer>();
        defaultValue.add(valueToCompare);
        defaultValue.add(valueToCompare);
        List<Integer> interval = originalInterval.stream()
                .map(integers -> Arrays.asList(integers))
                .filter(value -> valueToCompare >= value.get(0) && valueToCompare <= value.get(1))
                .findFirst().orElse(defaultValue);
        return new Integer[]{interval.get(0), interval.get(1)};
    }

    private int findIndex(List<Integer[]> originalInterval, Integer[] intervalToFind) {
        int index = 0;
        for (int i = 0; i < originalInterval.size(); i++) {
            if (Arrays.equals(originalInterval.get(i), intervalToFind)) {
                index = i;
                break;
            }
        }
        return index;
    }
}

