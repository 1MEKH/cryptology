package am.ua.cryptology.service.cipher;

import am.ua.cryptology.utils.CipherHelper;
import org.springframework.stereotype.Service;

@Service("Linear")
public class LinearCipherService extends CipherHelper {

    public String encrypt(String plainText, int key) {
        var ciphertext = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++) {
            char currentChar = plainText.charAt(i);

            if (Character.isLetter(currentChar)) {
                int offset = findOffset(currentChar);
                int p = currentChar - offset;
                int c = (p * key) % ALPHABET_SIZE;
                ciphertext.append((char) (c + offset));
            } else {
                ciphertext.append(currentChar);
            }
        }
        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, int key) {
        var plaintext = new StringBuilder();
        int inverseKey = findInverseOfKey(key);

        for (var i = 0; i < ciphertext.length(); i++) {
            var currentChar = ciphertext.charAt(i);
            if (Character.isLetter(currentChar)) {
                int offset = findOffset(currentChar);
                int c = currentChar - offset;
                int p = (c * inverseKey) % ALPHABET_SIZE;
                plaintext.append((char) (p + offset));
            } else {
                plaintext.append(currentChar);
            }
        }

        return plaintext.toString();
    }
}