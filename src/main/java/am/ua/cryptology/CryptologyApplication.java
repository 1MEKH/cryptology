package am.ua.cryptology;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class CryptologyApplication {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    public static void main(String[] args) {
        SpringApplication.run(CryptologyApplication.class, args);
    }

}
