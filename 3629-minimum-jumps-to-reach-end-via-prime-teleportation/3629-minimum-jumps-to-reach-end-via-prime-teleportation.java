class Solution {

    static final int LIMIT = 1000001;

    static int[] smallestPrime = generateSPF();

    static int[] generateSPF() {

        int[] factor = new int[LIMIT];

        for (int i = 0; i < LIMIT; i++) {
            factor[i] = i;
        }

        factor[0] = 0;
        factor[1] = 1;

        for (long num = 2; num * num < LIMIT; num++) {

            if (factor[(int) num] == num) {

                for (long mul = num * num; mul < LIMIT; mul += num) {

                    if (factor[(int) mul] == mul) {
                        factor[(int) mul] = (int) num;
                    }
                }
            }
        }

        return factor;
    }

    boolean checkPrime(int value) {

        return value >= 2 && smallestPrime[value] == value;
    }

    public int minJumps(int[] arr) {

        int size = arr.length;

        if (size == 1) return 0;

        int largest = 0;

        for (int val : arr) {
            largest = Math.max(largest, val);
        }

        boolean[] primeExists = new boolean[largest + 1];

        for (int val : arr) {

            if (checkPrime(val)) {
                primeExists[val] = true;
            }
        }

        HashMap<Integer, ArrayList<Integer>> factorMap = new HashMap<>();

        for (int pos = 0; pos < size; pos++) {

            int current = arr[pos];

            if (current == 1) continue;

            while (current > 1) {

                int divisor = smallestPrime[current];

                if (divisor <= largest && primeExists[divisor]) {

                    factorMap.putIfAbsent(divisor, new ArrayList<>());
                    factorMap.get(divisor).add(pos);
                }

                while (current % divisor == 0) {
                    current /= divisor;
                }
            }
        }

        int[] steps = new int[size];
        Arrays.fill(steps, -1);

        boolean[] primeUsed = new boolean[largest + 1];

        Queue<Integer> bfsQueue = new LinkedList<>();

        bfsQueue.offer(0);
        steps[0] = 0;

        while (!bfsQueue.isEmpty()) {

            int currentIndex = bfsQueue.poll();

            if (currentIndex == size - 1) {
                return steps[currentIndex];
            }

            if (currentIndex - 1 >= 0 && steps[currentIndex - 1] == -1) {

                steps[currentIndex - 1] = steps[currentIndex] + 1;
                bfsQueue.offer(currentIndex - 1);
            }

            if (currentIndex + 1 < size && steps[currentIndex + 1] == -1) {

                steps[currentIndex + 1] = steps[currentIndex] + 1;
                bfsQueue.offer(currentIndex + 1);
            }

            int element = arr[currentIndex];

            if (checkPrime(element) && !primeUsed[element]) {

                primeUsed[element] = true;

                if (factorMap.containsKey(element)) {

                    for (int nextPos : factorMap.get(element)) {

                        if (steps[nextPos] == -1) {

                            steps[nextPos] = steps[currentIndex] + 1;
                            bfsQueue.offer(nextPos);
                        }
                    }
                }
            }
        }

        return -1;
    }
}