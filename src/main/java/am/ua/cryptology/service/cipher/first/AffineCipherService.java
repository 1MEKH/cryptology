package am.ua.cryptology.service.cipher.first;

import am.ua.cryptology.utils.CipherHelper;
import org.springframework.stereotype.Service;

@Service("Affine")
public class AffineCipherService extends CipherHelper {

    //E(x) = (ax + b) % 26
    public String encrypt(String plainText, int a, int b) {
        var cipherText = new StringBuilder();
        for (int i = 0; i < plainText.length(); i++) {
            char currentChar = plainText.charAt(i);
            if (Character.isLetter(currentChar)) {
                int offset = findOffset(currentChar);
                int c = (a * (currentChar - offset) + b) % ALPHABET_SIZE;
                cipherText.append((char) (c + offset));
            } else {
                cipherText.append(currentChar);
            }
        }
        return cipherText.toString();
    }

    //D(x) = aInv * (x - b) % 26
    public String decrypt(String cipherText, int a, int b) {
        var plainText = new StringBuilder();
        for (int i = 0; i < cipherText.length(); i++) {
            char currentChar = cipherText.charAt(i);
            if (Character.isLetter(currentChar)) {
                int offset = findOffset(currentChar);
                int c = (findInverseOfKey(a) * (currentChar - b - offset)) % ALPHABET_SIZE;
                plainText.append((char) ((c + ALPHABET_SIZE) % ALPHABET_SIZE + offset));
            } else {
                plainText.append(currentChar);
            }
        }
        return plainText.toString();
    }

}
