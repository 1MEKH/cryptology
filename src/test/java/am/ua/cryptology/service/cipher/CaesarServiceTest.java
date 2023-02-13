package am.ua.cryptology.service.cipher;

import am.ua.cryptology.service.cipher.CaesarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CaesarService.class)
class CaesarServiceTest {

    @Autowired
    private CaesarService caesarService;

    private String text;
    private int key;

    @BeforeEach
    public void setUp() {
        text = "Hello";
        key = 3;
    }

    @Test
    @DisplayName("Encrypt -> Decrypt")
    void testEncryptDecrypt() {
        var encryptedText = caesarService.encrypt(text, key);
        var result = caesarService.decrypt(encryptedText, key);
        assertEquals(result, text);
    }

}