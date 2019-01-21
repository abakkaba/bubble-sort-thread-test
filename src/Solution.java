import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solution {

    public static final int MAX = 10000;

    public static int threadCount = 10;
    public static int[] testArray = new int[MAX];
    public static ArrayList<int[]> div = new ArrayList<>();

    static {
        for (int i = 0; i < Solution.testArray.length; i++) {
            testArray[i] = i;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        StringBuffer expectedResult = new StringBuffer();
        int[] d = null;
        for (int i = MAX - 1, j = 0; i >= 0; i--, j++) {
            expectedResult.append(i).append(" ");
            if ((i + 1) % 1000 == 0) {
                d = new int[1000];
                div.add(d);
                j = 0;
            }
            d[j] = i;
        }

        Date start = new Date();
        initThreads();
        Date end = new Date();

        StringBuffer result = new StringBuffer();

        for (int i = 0; i < div.size(); i++) {
            for (int j = 0; j < div.get(i).length; j++) {
                result.append(div.get(i)[j]).append(" ");
            }
        }
        System.out.println(result);
        System.out.println((result.toString()).equals(expectedResult.toString()));
        System.out.println(end.getTime() - start.getTime());
    }

    public static void initThreads() throws InterruptedException {
        List<Thread> threads = new ArrayList<Thread>(threadCount);
        for (int i = 0; i < threadCount; i++) threads.add(new SortThread(div.get(i)));
        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();
    }

    public static void sort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] < array[j]) {
                    int k = array[i];
                    array[i] = array[j];
                    array[j] = k;
                }
            }
        }
    }

    public static class SortThread extends Thread {
        public int[] ints;

        public SortThread(int[] array) {
            ints = array;
        }

        @Override
        public void run() {
            sort(ints);
        }
    }
}