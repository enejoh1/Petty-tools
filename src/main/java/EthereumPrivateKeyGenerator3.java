import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Arrays;

public class EthereumPrivateKeyGenerator3 {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("private_keys1million.txt"))) {
            for (int i = 0; i < 1000000; i++) {
                // Generate the ECDSA key pair
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC");
                ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
                keyPairGenerator.initialize(ecSpec, new SecureRandom());

                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                PrivateKey privateKey = keyPair.getPrivate();

                // Display the private key in hexadecimal format
                byte[] privateKeyBytes = privateKey.getEncoded();
                BigInteger privateKeyInt = new BigInteger(1, Arrays.copyOfRange(privateKeyBytes, privateKeyBytes.length - 32, privateKeyBytes.length));
                writer.write(privateKeyInt.toString(16));
                writer.newLine();
            }
        } catch (IOException | NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
    }
}
