package am.ua.cryptology.service.cipher.second;

import am.ua.cryptology.exception.ApiException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = HillCipherService.class)
class HillCipherServiceTest {

    @Autowired
    private HillCipherService hillCipherService;
    private String text;

    @BeforeEach
    public void setUp() {
        text = "HELLO";
    }

    @Test
    @DisplayName("Encrypt -> Decrypt")
    void testEncryptDecrypt() throws ApiException {
        var encryptedText = hillCipherService.encrypt(text);
        var result = hillCipherService.decrypt(encryptedText);
        assertEquals(text, result);
    }

}