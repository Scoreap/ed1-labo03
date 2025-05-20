package ed.lab;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class E01TopKFrequentElements {
    //primeroo
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (a, b) -> {
                    if (b[0] != a[0]) return Integer.compare(b[0], a[0]);
                    return Integer.compare(a[1], b[1]);
                }
        );

        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            maxHeap.add(new int[]{entry.getValue(), entry.getKey()});
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll()[1];
        }
        return result;
    }
}
