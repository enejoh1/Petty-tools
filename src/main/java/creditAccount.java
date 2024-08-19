import okhttp3.*;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;


public class creditAccount {
    static String bearerValue;

    public static void main(String[] args) throws Exception {
        testing();
    }

    static SecretKey testing() throws Exception {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("amount", "199.40");
        jsonObject.put("fee", "00.00");
        jsonObject.put("destAccount", "4021028080");
        jsonObject.put("description", "V_A_F");
        jsonObject.put("requestId", "01621790147105612201");
        jsonObject.put("destBankCode", "555");

        System.out.println(jsonObject);

        JSONObject jsonObjectPhoto = new JSONObject();

        jsonObjectPhoto.put("requestId", "000ed000470090018234");
        jsonObjectPhoto.put("imageBase64", "vagazito53A2TOGI?");
        jsonObjectPhoto.put("imageType", 1);
        jsonObjectPhoto.put("accountNo", "4021000025");

        String jsonStringPhoto = jsonObject.toString();
        String jsonString = jsonObject.toString();

        String plaintext = "1";
        String secretKey = "GZgs9Bmba2l*q6f&";
        String initializationVector = "L%NS*5cinTZ?qJy#";

        // Convert key and IV from hex strings to byte arrays
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        byte[] ivBytes = initializationVector.getBytes(StandardCharsets.UTF_8);

        // Create AES cipher with CBC mode and PKCS5 padding
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

        // Encrypt the plaintext
        byte[] encryptedBytes = cipher.doFinal(jsonString.getBytes(StandardCharsets.UTF_8));
        byte[] encryptedBytesPhoto = cipher.doFinal(jsonStringPhoto.getBytes(StandardCharsets.UTF_8));


        // Convert the encrypted bytes to a hex string
        String encryptedHexPhoto = byteArrayToHexString(encryptedBytesPhoto);
        String encryptedHex = byteArrayToHexString(encryptedBytes);
        System.out.println("Encrypted Hex: " + encryptedHex);

        try {
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            //////////////////// reset and request of key and IV
            RequestBody resetBody = RequestBody.create(mediaType, "{\n    \"ux\":\"testdrive@viral.com\"\n}");
            Request resetRequest = new Request.Builder()
                    .url("http://216.172.103.62:8056/Viral-GateWay/api/reset")
                    .method("POST", resetBody)
                    .addHeader("Content-Type", "application/json")
                    .build();
//            Response resetResponse = client.newCall(resetRequest).execute();
//            ResponseBody resetResponseBody = resetResponse.body();
//            if (resetResponseBody != null) {
//                System.out.println("Reset key and IV"+resetResponseBody.string());
//            }
//            resetResponse.close();
//            ////////////////

            ////////////////// generate JWT(java web token)
            RequestBody jwtBody = RequestBody.create("{\n    \"ux\":\"testdrive@viral.com\",\n     \"iv\": \"L%NS*5cinTZ?qJy#\",\n     \"key\": \"GZgs9Bmba2l*q6f&\"\n}", mediaType);
            Request jwtRequest = new Request.Builder()
                    .url("http://216.172.103.62:8056/Viral-GateWay/api/login")
                    .method("POST", jwtBody)
                    .addHeader("Content-Type", "application/json")
                    .build();
            Response jwtResponse = client.newCall(jwtRequest).execute();
            ResponseBody jwtResponseBody = jwtResponse.body();
            if (jwtResponseBody != null) {
                String jwtResponseBodyStr = jwtResponseBody.string();
                JSONObject jwtResponseBodyObj = new JSONObject(jwtResponseBodyStr);
                bearerValue = jwtResponseBodyObj.getString("Authorization");
                System.out.println(bearerValue);
            }
            jwtResponse.close();

            RequestBody body = RequestBody.create(encryptedHex, mediaType);
            Request request = new Request.Builder().url("http://216.172.103.62:8056/Viral-GateWay/api/credit").
                    method("POST", body).
                    addHeader("Authorization", bearerValue).
                    build();
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            int rcocde = response.code();
            if (responseBody != null) {
                System.out.println(jsonString);
                System.out.println(rcocde);
                System.out.println(responseBody.string());
            }
            response.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return secretKeySpec;
    }

    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}