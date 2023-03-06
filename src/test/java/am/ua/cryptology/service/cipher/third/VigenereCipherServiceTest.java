package am.ua.cryptology.service.cipher.third;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = VigenereCipherService.class)
class VigenereCipherServiceTest {

    @Autowired
    VigenereCipherService vigenereCipherService;

    private String text;
    private String key;

    @BeforeEach
    public void setUp() {
        text = "Hello Artem";
        key = "keyword";
    }

    @Test
    @DisplayName("Encrypt -> Decrypt")
    void testEncryptDecrypt() {
        var encryptedText = vigenereCipherService.encrypt(text, key);
        var result = vigenereCipherService.decrypt(encryptedText, key);
        assertEquals(result, text);
    }

}