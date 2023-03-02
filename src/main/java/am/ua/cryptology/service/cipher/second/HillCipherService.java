package am.ua.cryptology.service.cipher.second;

import am.ua.cryptology.exception.ApiException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("HillCipher")
public class HillCipherService {

    private static final int[][] keyMatrix = new int[][]{{3, 5}, {2, 7}};

    public String encrypt(String phrase) throws ApiException {
        int i;
        ArrayList<Integer> phraseToNum = new ArrayList<>();
        ArrayList<Integer> phraseEncoded = new ArrayList<>();

        // Delete all non-english characters, and convert phrase to upper case
        phrase = phrase.replaceAll("[^a-zA-Z]", "").toUpperCase();

        // If phrase length is not an even number, add "Q" to make it even
        if (phrase.length() % 2 == 1) {
            phrase += "Q";
        }

        // Check if the matrix is valid (det != 0)
        isValidMatrix();

        // Convert characters to numbers according to their
        for (i = 0; i < phrase.length(); i++) {
            phraseToNum.add(phrase.charAt(i) - (64));
        }

        // Find the product per pair of the phrase with the key matrix modulo 26
        for (i = 0; i < phraseToNum.size(); i += 2) {
            int x = (keyMatrix[0][0] * phraseToNum.get(i) + keyMatrix[0][1] * phraseToNum.get(i + 1)) % 26;
            int y = (keyMatrix[1][0] * phraseToNum.get(i) + keyMatrix[1][1] * phraseToNum.get(i + 1)) % 26;
            phraseEncoded.add(x == 0 ? 26 : x);
            phraseEncoded.add(y == 0 ? 26 : y);
        }

        return getResult(phraseEncoded);
    }

    public String decrypt(String phrase) throws ApiException {
        int[][] revKeyMatrix;
        ArrayList<Integer> phraseToNum = new ArrayList<>();
        ArrayList<Integer> phraseDecoded = new ArrayList<>();

        // Delete all non-english characters, and convert phrase to upper case
        phrase = phrase.replaceAll("[^a-zA-Z]", "").toUpperCase();


        // Check if the matrix is valid (det != 0)
        isValidMatrix();

        // Convert numbers to characters according to their
        for (int i = 0; i < phrase.length(); i++) {
            phraseToNum.add(phrase.charAt(i) - (64));
        }

        // Find the reverse key matrix
        revKeyMatrix = reverseMatrix();

        isValidReverseMatrix(revKeyMatrix);

        // Find the product per pair of the phrase with the reverse key matrix modulo 26
        for (int i = 0; i < phraseToNum.size(); i += 2) {
            phraseDecoded.add((revKeyMatrix[0][0] * phraseToNum.get(i) + revKeyMatrix[0][1] * phraseToNum.get(i + 1)) % 26);
            phraseDecoded.add((revKeyMatrix[1][0] * phraseToNum.get(i) + revKeyMatrix[1][1] * phraseToNum.get(i + 1)) % 26);
        }
        return getResult(phraseDecoded).replaceAll("Q", "");
    }

    private void isValidMatrix() throws ApiException {
        int det = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];

        // If det=0, throw exception and terminate
        if (det == 0) {
            throw new ApiException("Det equals to zero, invalid key matrix!");
        }
    }

    private void isValidReverseMatrix(int[][] reverseMatrix) throws ApiException {
        int[][] product = new int[2][2];

        // Find the product matrix of key matrix times reverse key matrix
        product[0][0] = (keyMatrix[0][0] * reverseMatrix[0][0] + keyMatrix[0][1] * reverseMatrix[1][0]) % 26;
        product[0][1] = (keyMatrix[0][0] * reverseMatrix[0][1] + keyMatrix[0][1] * reverseMatrix[1][1]) % 26;
        product[1][0] = (keyMatrix[1][0] * reverseMatrix[0][0] + keyMatrix[1][1] * reverseMatrix[1][0]) % 26;
        product[1][1] = (keyMatrix[1][0] * reverseMatrix[0][1] + keyMatrix[1][1] * reverseMatrix[1][1]) % 26;

        // Check if a=1 and b=0 and c=0 and d=1
        // If not, throw exception and terminate
        if (product[0][0] != 1 || product[0][1] != 0 || product[1][0] != 0 || product[1][1] != 1) {
            throw new ApiException("Invalid reverse matrix found!");
        }
    }

    private int[][] reverseMatrix() {
        int detmod26 = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0]) % 26; // Calc det
        int factor;
        int[][] reverseMatrix = new int[2][2];

        // Find the factor for which is true that
        // factor*det = 1 mod 26
        for (factor = 1; factor < 26; factor++) {
            if ((detmod26 * factor) % 26 == 1) {
                break;
            }
        }

        // Calculate the reverse key matrix elements using the factor found
        reverseMatrix[0][0] = keyMatrix[1][1] * factor % 26;
        reverseMatrix[0][1] = (26 - keyMatrix[0][1]) * factor % 26;
        reverseMatrix[1][0] = (26 - keyMatrix[1][0]) * factor % 26;
        reverseMatrix[1][1] = keyMatrix[0][0] * factor % 26;

        return reverseMatrix;
    }

    private String getResult(ArrayList<Integer> phrase) {
        int i;
        var result = new StringBuilder();

        // Loop for each pair
        for (i = 0; i < phrase.size(); i += 2) {
            result.append(Character.toChars(phrase.get(i) + (64)));
            result.append(Character.toChars(phrase.get(i + 1) + (64)));
        }
        return result.toString();
    }
}