package am.ua.cryptology.service.cipher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = AffineCipherService.class)
class AffineCipherServiceTest {

    @Autowired
    private AffineCipherService affineCipherService;

    private String text;
    private int a;
    private int b;

    @BeforeEach
    public void setUp() {
        text = "Hello";
        a = 5;
        b = 8;
    }

    @Test
    @DisplayName("Encrypt -> Decrypt")
    void testEncryptDecrypt() {
        var encryptedText = affineCipherService.encrypt(text, a, b);
        var result = affineCipherService.decrypt(encryptedText, a, b);
        assertEquals(result, text);
    }
}