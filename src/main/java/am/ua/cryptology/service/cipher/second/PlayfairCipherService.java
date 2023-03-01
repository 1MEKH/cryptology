package am.ua.cryptology.service.cipher.second;

import org.springframework.stereotype.Service;

@Service("Playfair")
public class PlayfairCipherService {

    public String encrypt(String plaintext, String keyword) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = plaintext.replaceAll("J", "I"); // replace J with I

        // Pad plaintext if length is odd
        if (plaintext.length() % 2 == 1) {
            plaintext += "X";
        }
        var matrix = initMatrix(keyword);

        // Encrypt plaintext
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char c1 = plaintext.charAt(i);
            char c2 = plaintext.charAt(i + 1);
            int[] pos1 = findPosition(c1, matrix);
            int[] pos2 = findPosition(c2, matrix);
            if (pos1[0] == pos2[0]) { // same row
                ciphertext.append(matrix[pos1[0]][(pos1[1] + 1) % 5]);
                ciphertext.append(matrix[pos2[0]][(pos2[1] + 1) % 5]);
            } else if (pos1[1] == pos2[1]) { // same column
                ciphertext.append(matrix[(pos1[0] + 1) % 5][pos1[1]]);
                ciphertext.append(matrix[(pos2[0] + 1) % 5][pos2[1]]);
            } else { // different row and column
                ciphertext.append(matrix[pos1[0]][pos2[1]]);
                ciphertext.append(matrix[pos2[0]][pos1[1]]);
            }
        }

        return ciphertext.toString();
    }


    public String decrypt(String ciphertext, String keyword) {
        ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
        ciphertext = ciphertext.replaceAll("J", "I"); // replace J with I

        var matrix = initMatrix(keyword);

        // Decrypt ciphertext
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char c1 = ciphertext.charAt(i);
            char c2 = ciphertext.charAt(i + 1);
            int[] pos1 = findPosition(c1, matrix);
            int[] pos2 = findPosition(c2, matrix);
            if (pos1[0] == pos2[0]) { // same row
                plaintext.append(matrix[pos1[0]][(pos1[1] + 4) % 5]);
                plaintext.append(matrix[pos2[0]][(pos2[1] + 4) % 5]);
            } else if (pos1[1] == pos2[1]) { // same column
                plaintext.append(matrix[(pos1[0] + 4) % 5][pos1[1]]);
                plaintext.append(matrix[(pos2[0] + 4) % 5][pos2[1]]);
            } else { // different row and column
                plaintext.append(matrix[pos1[0]][pos2[1]]);
                plaintext.append(matrix[pos2[0]][pos1[1]]);
            }
        }
        if (plaintext.charAt(plaintext.length() - 1) == 'X') {
            plaintext.deleteCharAt(plaintext.length() - 1);
        }

        return plaintext.toString();
    }

    private int[] findPosition(char c, char[][] matrix) {
        int[] pos = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    pos[0] = i;
                    pos[1] = j;
                    break;
                }
            }
        }
        return pos;
    }

    private char[][] initMatrix(String keyword) {
        // Convert keyword to uppercase and remove duplicate letters
        keyword = keyword.toUpperCase().replaceAll("[^A-Z]", "");
        StringBuilder sb = new StringBuilder(keyword);
        for (int i = 0; i < 26; i++) {
            char c = (char) ('A' + i);
            if (c == 'J') {
                continue; // skip J (same as I)
            }
            if (sb.indexOf(Character.toString(c)) == -1) {
                sb.append(c);
            }
        }
        keyword = sb.toString();

        // Create cipher matrix
        var matrix = new char[5][5];
        int k = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = keyword.charAt(k++);
            }
        }
        return matrix;
    }
}