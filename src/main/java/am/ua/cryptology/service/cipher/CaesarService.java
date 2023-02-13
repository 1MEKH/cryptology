package am.ua.cryptology.service.cipher;

import am.ua.cryptology.utils.CipherHelper;
import org.springframework.stereotype.Service;

@Service("Caesar")
public class CaesarService extends CipherHelper {

    public String encrypt(String plainText, int key) {
        var cipherText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char currentChar = plainText.charAt(i);
            if (Character.isLetter(currentChar)) {
                int offset = findOffset(currentChar);
                int c = ((int) currentChar + key - offset) % ALPHABET_SIZE;
                cipherText.append((char) (c + offset));
            } else {
                cipherText.append(currentChar);
            }
        }
        return cipherText.toString();
    }

    public String decrypt(String cipherText, int key) {
        return encrypt(cipherText, ALPHABET_SIZE - key);
    }

}
