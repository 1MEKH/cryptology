package am.ua.cryptology.service.cipher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LinearCipherService.class)
class LinearCipherServiceTest {

    @Autowired
    private LinearCipherService linearCipherService;

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
        var encryptedText = linearCipherService.encrypt(text, key);
        var result = linearCipherService.decrypt(encryptedText, key);
        assertEquals(result, text);
    }
}