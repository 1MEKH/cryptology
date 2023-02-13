package am.ua.cryptology.service.io;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@AllArgsConstructor
@Service
public class IOService {

    private final Scanner scanner;

    public String askTextForCipherWithOneKey() {
        System.out.println("Write text & key for encrypt/decrypt, separated by '|' :: ");
        return scanner.nextLine();
    }

    public String askTextForCipherWithTwoKey() {
        System.out.println("Write text & a & b keys for encrypt/decrypt, separated by '|' :: ");
        return scanner.nextLine();
    }

}
