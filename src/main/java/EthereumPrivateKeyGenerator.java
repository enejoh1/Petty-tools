import java.io.*;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class EthereumPrivateKeyGenerator {


    public static void main(String[] args) throws FileNotFoundException {
        // Specify the file path where you want to save the private keys
        String filePath = "private_keyz.txt";
        FileOutputStream fileOutputStream = new FileOutputStream("private_keys.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Create a set to store generated private keys
            Set<String> generatedPrivateKeys = new HashSet<>();




            for (int i = 0; i < 20000000; i++) {
                // Generate a random private key
                String privateKey = generateRandomPrivateKey();


                // Check if the private key is not already generated
                if (!generatedPrivateKeys.contains(privateKey)) {
                    // Write the generated private key to the file
                    writer.write(privateKey);
                    writer.newLine();

                    // Add the generated private key to the set
                    generatedPrivateKeys.add(privateKey);
                }
            }
            System.out.println("Private keys have been generated and saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to generate a random private key
    private static String generateRandomPrivateKey() {
        // Define the characters used in hexadecimal representation
        String hexChars = "0123456789abcdef";

        // Use SecureRandom for cryptographic strength random number generation
        SecureRandom random = new SecureRandom();

        // Create a StringBuilder to hold the generated private key
        StringBuilder privateKeyBuilder = new StringBuilder();

        // Generate random characters until the private key reaches the desired length
        while (privateKeyBuilder.length() < 64) {
            // Append a random hexadecimal character from the hexChars string
            privateKeyBuilder.append(hexChars.charAt(random.nextInt(hexChars.length())));
        }

        // Convert the StringBuilder to a String and return
        return privateKeyBuilder.toString();
    }
}