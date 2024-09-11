import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import okhttp3.*;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;



public class sunTrustVirtualAccount {

    String randomNumber;
    String api_key = "QsHysem8irgDiF8bVG6P_ad940606bc9e4a5399185b08ebd31ea6";
    String secret_key = "FdgFNBjO1UGn9RHt";

    public static void main(String[] args) {
        System.out.println(new sunTrustVirtualAccount().sunTrustVirtualAcc());
    }

    public String generateRandom() {
        Random rand = new Random();
        long min = 100000000000000L;
        long max = 999999999999999L;
        long requestRef = min + ((long)(rand.nextDouble() * (max - min)));
        System.out.println(requestRef);
        return String.valueOf(requestRef);
    }
    public String getMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptV2(String data) {
        try {
            String method = "DESede/CBC/PKCS5Padding";
            byte[] source = "LdgUEP0VvyvoT4qA".getBytes(StandardCharsets.UTF_16LE);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] encryptionKey = md.digest(source);
            byte[] keyBytes = Arrays.copyOf(encryptionKey, 24);
            for (int j = 0, k = 16; j < 8;) {
                keyBytes[k++] = keyBytes[j++];
            }
            SecretKey secretKey = new SecretKeySpec(keyBytes, 0, 24, "DESede");
            IvParameterSpec ivSpec = new IvParameterSpec(new byte[8]);
            Cipher cipher = Cipher.getInstance(method);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] encData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_16LE));

            return new String(Base64.getEncoder().encode(encData));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String payLoadBody() {
        randomNumber = generateRandom();
        String encryptedBvn = encryptV2("22303448521");
        //22153225112
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("request_ref", new JsonPrimitive(randomNumber));
        jsonObject.add("request_type", new JsonPrimitive("open_account"));

        JsonObject auth = new JsonObject();
        auth.add("type", null);
        auth.add("secure", null);
        auth.addProperty("auth_provider", "SunTrustVirtual");
        auth.add("route_mode", null);


        JsonObject transaction = new JsonObject();
        transaction.add("mock_mode", new JsonPrimitive("inspect"));
        transaction.add("transaction_ref", new JsonPrimitive(randomNumber + "21"));
        transaction.add("transaction_desc", new JsonPrimitive("A random transaction"));
        transaction.add("transaction_ref_parent", null);
        transaction.add("amount", new JsonPrimitive(0));

        JsonObject customer = new JsonObject();
        customer.add("customer_ref", new JsonPrimitive("08067242034"));
        customer.add("firstname", new JsonPrimitive("chris"));
        customer.add("surname", new JsonPrimitive("abu"));
        customer.add("email", new JsonPrimitive("enejoh@gmail.com"));
        customer.add("mobile_no", new JsonPrimitive("08067242034"));
        transaction.add("customer", customer);

        JsonObject meta = new JsonObject();
        meta.add( "a_key", new JsonPrimitive("a_meta_value_1"));
        meta.add( "b_key", new JsonPrimitive("a_meta_value_2"));
        transaction.add("meta", meta);

        JsonObject details = new JsonObject();
        details.add("name_on_account", new JsonPrimitive("John Doe James"));
        details.add("middlename", new JsonPrimitive("Doe"));
        details.add("dob", new JsonPrimitive("2005-05-13"));
        details.add("gender", new JsonPrimitive("M"));
        details.add("title", new JsonPrimitive("Mr"));
        details.add("address_line_1", new JsonPrimitive("23, Okon street, Ikeja"));
        details.add("address_line_2", new JsonPrimitive("Ikeja"));
        details.add("city", new JsonPrimitive("Mushin"));
        details.add("state", new JsonPrimitive("Lagos"));
        details.add("country", new JsonPrimitive("Nigeria"));

        transaction.add("details", details);

        jsonObject.add("transaction", transaction);

        Gson gsonPretty = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
        String jsonPretty = gsonPretty.toJson(jsonObject);
        System.out.println(jsonPretty);

        // Convert to JSON string
        Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create();
        String json = gson.toJson(jsonObject);

        // Print the JSON in a structured format
        System.out.println(json);

        return json;
    }

    public String sunTrustVirtualAcc() {
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody requestBody = RequestBody.create(payLoadBody(), mediaType);
            Request request = new Request.Builder().url("https://api.onepipe.io/v2/transact")
                    .method("POST", requestBody)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + api_key)
                    .addHeader("Signature", getMD5Hash(randomNumber + ";" + secret_key))
                    .build();
            Response response = client.newCall(request).execute();
            if (response.body() != null) {
                System.out.println("");
                return response.body().string();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
