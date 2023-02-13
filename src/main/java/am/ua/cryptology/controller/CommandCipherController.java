package am.ua.cryptology.controller;

import am.ua.cryptology.exception.ApiException;
import am.ua.cryptology.service.cipher.AffineCipherService;
import am.ua.cryptology.service.cipher.CaesarService;
import am.ua.cryptology.service.cipher.LinearCipherService;
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

    private int parseKey(String value) throws ApiException {
        try {
            return parseInt(value);
        } catch (NumberFormatException e) {
            throw new ApiException("Parse value to int error: " + e.getMessage());
        }
    }
}
