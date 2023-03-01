package am.ua.cryptology.service.cipher.second;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PlayfairCipherService.class)
class PlayfairCipherTest {

    @Autowired
    private PlayfairCipherService playfairCipher;

    private String text;
    private String key;

    @BeforeEach
    public void setUp() {
        text = "HELLO";
        key = "KEYWORD";
    }

    @Test
    @DisplayName("Encrypt -> Decrypt")
    void testEncryptDecrypt() {
        var encryptedText = playfairCipher.encrypt(text, key);
        var result = playfairCipher.decrypt(encryptedText, key);
        assertEquals(result, text);
    }
}


