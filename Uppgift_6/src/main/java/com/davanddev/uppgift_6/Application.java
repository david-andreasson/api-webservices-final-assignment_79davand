package com.davanddev.uppgift_6;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("generate-key")) {
            generateKey();
            return;
        }

        // Load environment variables only from .env
        Dotenv dotenv = Dotenv.load();

        // Check required properties from .env only
        checkProperty(dotenv, "JWT_SECRET", "your_jwt_secret");
        checkProperty(dotenv, "GOOGLE_CLIENT_ID", "your_google_client_id");
        checkProperty(dotenv, "GOOGLE_CLIENT_SECRET", "your_google_client_secret");

//        System.out.println("JWT_SECRET: " + System.getProperty("JWT_SECRET"));
//        System.out.println("GOOGLE_CLIENT_ID: " + System.getProperty("GOOGLE_CLIENT_ID"));
//        System.out.println("GOOGLE_CLIENT_SECRET: " + System.getProperty("GOOGLE_CLIENT_SECRET"));

        SpringApplication.run(Application.class, args);
    }

    private static void checkProperty(Dotenv dotenv, String key, String defaultValue) {
        String value = dotenv.get(key);
        if (value == null || value.equals(defaultValue)) {
            System.out.println("Please set " + key + " in your .env file");
            System.exit(1);
        }
        System.setProperty(key, value);
    }

    public static void generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println(encodedKey);
    }
}
