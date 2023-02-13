package am.ua.cryptology.utils;

public abstract class CipherHelper {
    protected static final int ALPHABET_SIZE = 26;

    protected int findOffset(char currentChar) {
        return Character.isUpperCase(currentChar) ? 'A' : 'a';
    }

    protected int findInverseOfKey(int key) {
        int aInverse = 0;
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            if ((key * i) % ALPHABET_SIZE == 1) {
                aInverse = i;
                break;
            }
        }
        return aInverse;
    }

}
