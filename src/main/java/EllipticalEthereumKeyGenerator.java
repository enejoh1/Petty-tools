import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.util.encoders.Hex;

import java.math.BigInteger;
import java.security.*;
import java.io.FileWriter;
import java.io.PrintWriter;

public class EllipticalEthereumKeyGenerator {

    public static void main(String[] args) {
        // Add BouncyCastle Provider
        Security.addProvider(new BouncyCastleProvider());

        try {
            // Define the elliptic curve parameters for secp256k1
            ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1");

            // Create the KeyPairGenerator with the specified parameters
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            keyPairGenerator.initialize(ecSpec, new SecureRandom());

            // Open file for writing
            try (PrintWriter writer = new PrintWriter(new FileWriter("private_keysbabe.txt"))) {
                // Generate 1,000,000 private keys
                for (int i = 0; i < 300000; i++) {
                    // Generate the key pair
                    KeyPair keyPair = keyPairGenerator.generateKeyPair();
                    PrivateKey privateKey = keyPair.getPrivate();

                    // Extract the raw private key bytes (32 bytes)
                    BigInteger privateKeyInt = ((org.bouncycastle.jce.interfaces.ECPrivateKey) privateKey).getD();
                    byte[] privateKeyBytes = privateKeyInt.toByteArray();

                    // Ensure the private key is 32 bytes long
                    if (privateKeyBytes.length != 32) {
                        byte[] tmp = new byte[32];
                        if (privateKeyBytes.length > 32) {
                            System.arraycopy(privateKeyBytes, privateKeyBytes.length - 32, tmp, 0, 32);
                        } else {
                            System.arraycopy(privateKeyBytes, 0, tmp, 32 - privateKeyBytes.length, privateKeyBytes.length);
                        }
                        privateKeyBytes = tmp;
                    }

                    // Write the private key to the file
                    writer.println(Hex.toHexString(privateKeyBytes));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
