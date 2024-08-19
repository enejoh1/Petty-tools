import okhttp3.OkHttpClient;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class checkEthBalance {
    public static void main(String[] args) {
        String nodeUrl = "https://eth-mainnet.g.alchemy.com/v2/uIaig7j13ajmRfEqjT6SGvNYpPwcqX--";
        //String nodeUrl = "https://holesky.infura.io/v3/b522872beb3046a2b5f1e26ffe50db77";

        // File paths
        String publicKeysFile = "ethereum_public_keys.txt";
        String outputFile = "wallet_balancesTest.txt";

        try {
            // Read public keys from file
            List<String> publicKeys = readPublicKeysFromFile(publicKeysFile);

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
            for (String publicKey : publicKeys) {
                retryBalanceCheck(web3, publicKey, outputFile, count);
                count++;  // Increment counter
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Operation failed");
        }
    }

    private static List<String> readPublicKeysFromFile(String filename) throws IOException {
        List<String> publicKeys = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                publicKeys.add(line.trim());
            }
        }
        return publicKeys;
    }

    private static void retryBalanceCheck(Web3j web3, String publicKey, String outputFile, int count) {
        final int maxRetries = 3;
        int attempt = 0;
        boolean success = false;

        while (attempt < maxRetries && !success) {
            try {
                EthGetBalance ethGetBalance = web3.ethGetBalance(publicKey, DefaultBlockParameterName.LATEST).send();

                BigInteger balanceWei = ethGetBalance.getBalance();
                BigDecimal balanceEther = Convert.fromWei(balanceWei.toString(), Convert.Unit.ETHER);

                System.out.println(count + ". Address: " + publicKey + ", Balance: " + balanceEther + " ether");

                // Save to file if balance is greater than 0 ether
                if (balanceEther.compareTo(BigDecimal.ZERO) > 0) {
                    saveToFile(outputFile, publicKey, balanceWei, balanceEther);
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

    private static void saveToFile(String filename, String publicKey, BigInteger balanceWei, BigDecimal balanceEther) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write("Public Key: " + publicKey + "\n");
            writer.write("Balance: " + balanceWei + " wei (" + balanceEther + " ether)\n");
            writer.write("\n");
            System.out.println("Saved wallet information to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save wallet information");
        }
    }
}
