package com.davanddev.uppgift_6;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Main application class for the API.
 * <p>
 * This class loads environment variables from the .env file, verifies that required properties are set,
 * and starts the Spring Boot application.
 * It also provides a method to generate a secure random key.
 * </p>
 */
@SpringBootApplication
public class Application {

    /**
     * Main entry point for the Spring Boot application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("generate-key")) {
            generateKey();
            return;
        }

        // Load environment variables from .env file.
        Dotenv dotenv = Dotenv.load();

        // Verify required properties from the .env file.
        checkProperty(dotenv, "JWT_SECRET", "your_jwt_secret");
        checkProperty(dotenv, "GOOGLE_CLIENT_ID", "your_google_client_id");
        checkProperty(dotenv, "GOOGLE_CLIENT_SECRET", "your_google_client_secret");

        SpringApplication.run(Application.class, args);
    }

    /**
     * Checks if the specified environment property is set and does not match the default value.
     * If the property is missing or still set to the default, the application will exit.
     *
     * @param dotenv       The Dotenv instance loaded from the .env file.
     * @param key          The environment variable key.
     * @param defaultValue The default value that should be replaced.
     */
    private static void checkProperty(Dotenv dotenv, String key, String defaultValue) {
        String value = dotenv.get(key);
        if (value == null || value.equals(defaultValue)) {
            System.out.println("Please set " + key + " in your .env file");
            System.exit(1);
        }
        System.setProperty(key, value);
    }

    /**
     * Generates a secure random key and prints it to the console.
     */
    public static void generateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];
        secureRandom.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        System.out.println(encodedKey);
    }
}
