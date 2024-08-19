import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;

public class EthereumPrivateKeyGenerator2 {

    public static void main(String[] args) {
        try {
            generateAndSavePrivateKeys(200000, "ethereum_private_keys.txt");
            System.out.println("90000 Ethereum private keys have been generated and saved to ethereum_private_keys.txt");
        } catch (IOException e) {
            System.err.println("Error while writing to file: " + e.getMessage());
        }
    }

    public static void generateAndSavePrivateKeys(int numberOfKeys, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < numberOfKeys; i++) {
                String privateKey = generateEthereumPrivateKey();
                writer.write(privateKey);
                writer.newLine();
            }
        }
    }

    public static String generateEthereumPrivateKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] privateKey = new byte[32]; // 256 bits
        secureRandom.nextBytes(privateKey);
        return bytesToHex(privateKey);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
