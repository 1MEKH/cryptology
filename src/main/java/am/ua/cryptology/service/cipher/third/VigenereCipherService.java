package am.ua.cryptology.service.cipher.third;

import am.ua.cryptology.utils.CipherHelper;
import org.springframework.stereotype.Service;

@Service("Vigenere")
public class VigenereCipherService extends CipherHelper {

    public String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();

        // Keep track of which letter in the key to use for encryption
        int keyIndex = 0;

        for (int i = 0; i < plaintext.length(); i++) {
            char currentChar = plaintext.charAt(i);

            // Check if current character is a letter
            if (Character.isLetter(currentChar)) {
                // Find offset based on letter case
                int offset = findOffset(currentChar);

                // Convert current character and key character to their respective alphabetical indices
                int currentCharIndex = currentChar - offset;
                int keyCharIndex = key.charAt(keyIndex) - offset;

                // Calculate the index of the encrypted character
                int encryptedCharIndex = (currentCharIndex + keyCharIndex) % ALPHABET_SIZE;

                // Find the encrypted character based on the index and offset
                char encryptedChar = (char) (encryptedCharIndex + offset);

                // Add the encrypted character to the ciphertext
                ciphertext.append(encryptedChar);

                // Increment the key index
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                // Add non-letter character to the ciphertext without encryption
                ciphertext.append(currentChar);
            }
        }

        return ciphertext.toString();
    }

    public String decrypt(String ciphertext, String key) {
        StringBuilder plaintext = new StringBuilder();

        // Keep track of which letter in the key to use for decryption
        int keyIndex = 0;

        for (int i = 0; i < ciphertext.length(); i++) {
            char currentChar = ciphertext.charAt(i);

            // Check if current character is a letter
            if (Character.isLetter(currentChar)) {
                // Find offset based on letter case
                int offset = findOffset(currentChar);

                // Convert current character and key character to their respective alphabetical indices
                int currentCharIndex = currentChar - offset;
                int keyCharIndex = key.charAt(keyIndex) - offset;

                // Calculate the index of the decrypted character
                int decryptedCharIndex = (currentCharIndex - keyCharIndex + ALPHABET_SIZE) % ALPHABET_SIZE;

                // Find the decrypted character based on the index and offset
                char decryptedChar = (char) (decryptedCharIndex + offset);

                // Add the decrypted character to the plaintext
                plaintext.append(decryptedChar);

                // Increment the key index
                keyIndex = (keyIndex + 1) % key.length();
            } else {
                // Add non-letter character to the plaintext without decryption
                plaintext.append(currentChar);
            }
        }

        return plaintext.toString();
    }
}
