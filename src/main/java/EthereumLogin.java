import okhttp3.OkHttpClient;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class EthereumLogin {
    public static void main(String[] args) {

        String nodeUrl = "https://eth-mainnet.g.alchemy.com/v2/uIaig7j13ajmRfEqjT6SGvNYpPwcqX--";
      //  String nodeUrl = "https://eth-mainnet.g.alchemy.com/v2/1xrCIe_3cW6yCUn2lJ_0S5mguhzbg3h8"
       // String nodeUrl = "https://holesky.infura.io/v3/b522872beb3046a2b5f1e26ffe50db77";

        // File paths
        String privateKeysFile = "private_keysbabe.txt";
        String outputFile = "wallet_balances.txt";

        try {
            // Read private keys from file
            List<String> privateKeys = readPrivateKeysFromFile(privateKeysFile);

            // Configure OkHttpClient with increased timeouts
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

            // Connect to the Ethereum network with the custom OkHttpClient
            HttpService httpService = new HttpService(nodeUrl, httpClient, false);
            Web3j web3 = Web3j.build(httpService);

            // Get the client version to verify connection
            Web3ClientVersion clientVersion = web3.web3ClientVersion().send();
            System.out.println("Connected to Ethereum node: " + clientVersion.getWeb3ClientVersion());

            // Check balances and save to file if balance is above 0 wei
            int count = 0;  // Initialize counter
            for (String privateKey : privateKeys) {
                retryBalanceCheck(web3, privateKey, outputFile, count);
                count++;  // Increment counter

                // Remove the used private key from the file
               // removeProcessedPrivateKey(privateKeysFile, privateKey);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Operation failed");
        }
    }

    private static List<String> readPrivateKeysFromFile(String filename) throws IOException {
        List<String> privateKeys = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                privateKeys.add(line.trim());
            }
        }
        return privateKeys;
    }

    private static void retryBalanceCheck(Web3j web3, String privateKey, String outputFile, int count) {
        final int maxRetries = 10;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            try {
                Credentials credentials = Credentials.create(privateKey);
                String ethAddress = credentials.getAddress();
                EthGetBalance ethGetBalance = web3.ethGetBalance(ethAddress, DefaultBlockParameterName.LATEST).send();
                BigInteger balance = ethGetBalance.getBalance();

                System.out.println(count + ". Address: " + ethAddress + ", Balance: " + balance + " wei, Private key => " + privateKey);

                if (balance.compareTo(BigInteger.ZERO) > 0) {
                    saveToFile(outputFile, privateKey, balance);
                }

                success = true; // If no exception occurs, set success to true
            } catch (Exception e) {
                attempt++;
                System.out.println("Attempt " + attempt + " failed, retrying...");
                if (attempt == maxRetries) {
                    System.out.println("Failed after " + maxRetries + " attempts");
                    e.printStackTrace();
                }
            }
        }
    }

    private static void saveToFile(String filename, String privateKey, BigInteger balance) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("Private Key: " + privateKey + "\n");
            writer.write("Balance: " + balance + " wei\n");
            writer.write("\n");
            System.out.println("Saved wallet information to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save wallet information");
        }
    }

    private static void removeProcessedPrivateKey(String filename, String privateKey) {
        try {
            File inputFile = new File(filename);
            File tempFile = new File("temp_" + filename);

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    // Trim newline when comparing with private key
                    String trimmedLine = currentLine.trim();
                    if (trimmedLine.equals(privateKey)) {
                        continue;
                    }
                    writer.write(currentLine + System.getProperty("line.separator"));
                }
            }

            // Delete the original file
            if (!inputFile.delete()) {
                System.out.println("Could not delete file: " + filename);
            }

            // Rename the new file to the filename
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename file to: " + filename);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to remove processed private key");
        }
    }
}
