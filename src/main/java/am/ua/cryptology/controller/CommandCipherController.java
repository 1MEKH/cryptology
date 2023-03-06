package am.ua.cryptology.controller;

import am.ua.cryptology.exception.ApiException;
import am.ua.cryptology.service.cipher.first.AffineCipherService;
import am.ua.cryptology.service.cipher.first.CaesarService;
import am.ua.cryptology.service.cipher.first.LinearCipherService;
import am.ua.cryptology.service.cipher.second.HillCipherService;
import am.ua.cryptology.service.cipher.second.PlayfairCipherService;
import am.ua.cryptology.service.cipher.third.VigenereCipherService;
import am.ua.cryptology.service.io.IOService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import static java.lang.Integer.parseInt;

@ShellComponent
@AllArgsConstructor
public class CommandCipherController {

    private static final String SEPARATOR = "\\|";
    private final CaesarService caesarService;
    private final LinearCipherService linearCipherService;
    private final AffineCipherService affineCipherService;
    private final PlayfairCipherService playfairCipherService;
    private final HillCipherService hillCipherService;

    private final VigenereCipherService vigenereCipherService;
    private final IOService ioService;

    @ShellMethod(value = "Caesar cipher encrypt command", key = {"-caesar encrypt:", "--c e"})
    public String caesarEncrypt() throws ApiException {
        var response = ioService.askTextForCipherWithOneKey().split(SEPARATOR);
        return caesarService.encrypt(response[0], parseKey(response[1]));
    }

    @ShellMethod(value = "Caesar cipher decrypt command", key = {"-caesar decrypt:", "--c d"})
    public String caesarDecrypt() throws ApiException {
        var response = ioService.askTextForCipherWithOneKey().split(SEPARATOR);
        return caesarService.decrypt(response[0], parseKey(response[1]));
    }

    @ShellMethod(value = "Linear cipher encrypt command", key = {"-linear encrypt:", "--l e"})
    public String linearEncrypt() throws ApiException {
        var response = ioService.askTextForCipherWithOneKey().split(SEPARATOR);
        return linearCipherService.encrypt(response[0], parseKey(response[1]));
    }

    @ShellMethod(value = "Linear cipher decrypt command", key = {"-linear decrypt:", "--l d"})
    public String linearDecrypt() throws ApiException {
        var response = ioService.askTextForCipherWithOneKey().split(SEPARATOR);
        return linearCipherService.decrypt(response[0], parseKey(response[1]));
    }

    @ShellMethod(value = "Affine cipher encrypt command", key = {"-affine encrypt:", "--a e"})
    public String affineEncrypt() throws ApiException {
        var response = ioService.askTextForCipherWithTwoKey().split(SEPARATOR);
        return affineCipherService.encrypt(response[0], parseKey(response[1]), parseKey(response[2]));
    }

    @ShellMethod(value = "Affine cipher decrypt command", key = {"-affine decrypt:", "--a d"})
    public String affineDecrypt() throws ApiException {
        var response = ioService.askTextForCipherWithTwoKey().split(SEPARATOR);
        return affineCipherService.decrypt(response[0], parseKey(response[1]), parseKey(response[2]));
    }

    @ShellMethod(value = "Playfair cipher encrypt command", key = {"-playfair encrypt:", "--p e"})
    public String playfairEncrypt() {
        var response = ioService.askTextForCipherWithOneKey().split(SEPARATOR);
        var text = response[0].split(" ");
        var sb = new StringBuilder();
        for (var word : text) {
            sb.append(playfairCipherService.encrypt(word, response[1])).append(" ");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Playfair cipher decrypt command", key = {"-playfair decrypt:", "--p d"})
    public String playfairDecrypt() {
        var response = ioService.askTextForCipherWithOneKey().split(SEPARATOR);
        var text = response[0].split(" ");
        var sb = new StringBuilder();
        for (var word : text) {
            sb.append(playfairCipherService.decrypt(word, response[1])).append(" ");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Hill cipher encrypt command", key = {"-hill encrypt:", "--h e"})
    public String hillEncrypt() throws ApiException {
        var response = ioService.askTextForCipher();
        var sb = new StringBuilder();
        for (var word : response.split(" ")) {
            sb.append(hillCipherService.encrypt(word)).append(" ");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Hill cipher decrypt command", key = {"-hill decrypt:", "--h d"})
    public String hillDecrypt() throws ApiException {
        var response = ioService.askTextForCipher();
        var sb = new StringBuilder();
        for (var word : response.split(" ")) {
            sb.append(hillCipherService.decrypt(word)).append(" ");
        }
        return sb.toString();
    }

    @ShellMethod(value = "Vigenere cipher encrypt command", key = {"-vigenere encrypt:", "--v e"})
    public String vigenereEncrypt() throws ApiException {
        var response = ioService.askTextForCipherWithOneKey().split(SEPARATOR);
        return vigenereCipherService.encrypt(response[0], response[1]);
    }

    @ShellMethod(value = "Vigenere cipher decrypt command", key = {"-vigenere decrypt:", "--v d"})
    public String vigenereDecrypt() throws ApiException {
        var response = ioService.askTextForCipherWithOneKey().split(SEPARATOR);
        return vigenereCipherService.decrypt(response[0], response[1]);
    }

    private int parseKey(String value) throws ApiException {
        try {
            return parseInt(value);
        } catch (NumberFormatException e) {
            throw new ApiException("Parse value to int error: " + e.getMessage());
        }
    }
}
