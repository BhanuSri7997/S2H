import java.util.*;

class Fancy {

    private static final long MOD = 1000000007L;

    private List<Long> sequence;
    private long mulFactor;
    private long addFactor;

    public Fancy() {
        sequence = new ArrayList<>();
        mulFactor = 1;
        addFactor = 0;
    }

    private long power(long base, long exp) {
        long result = 1;
        base %= MOD;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % MOD;
            }
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return result;
    }

    private long modInverse(long num) {
        return power(num, MOD - 2);
    }

    public void append(int value) {
        long adjusted = (value - addFactor + MOD) % MOD;
        adjusted = (adjusted * modInverse(mulFactor)) % MOD;
        sequence.add(adjusted);
    }

    public void addAll(int inc) {
        addFactor = (addFactor + inc) % MOD;
    }

    public void multAll(int m) {
        mulFactor = (mulFactor * m) % MOD;
        addFactor = (addFactor * m) % MOD;
    }

    public int getIndex(int index) {
        if (index >= sequence.size()) {
            return -1;
        }

        long original = sequence.get(index);
        long result = (mulFactor * original + addFactor) % MOD;

        return (int) result;
    }
}