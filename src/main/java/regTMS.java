

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;


public class regTMS {

    public static void main(String[] args) throws Exception {
        testing();
    }
    static void testing() throws Exception {
        JSONObject jsonObjectTMS = new JSONObject();

        jsonObjectTMS.put("serial", "982209027vcew8268");
        jsonObjectTMS.put("device", "QPOS Mini");
        jsonObjectTMS.put("first_name", "test");
        jsonObjectTMS.put("other_names", "test2");
        jsonObjectTMS.put("email", "test1@gmail.com");
        jsonObjectTMS.put("phone", "08076425678");
        jsonObjectTMS.put("gender", "MALE");
        jsonObjectTMS.put("dob", "1973-03-23");
        jsonObjectTMS.put("state", "Ogun");
        jsonObjectTMS.put("role", "AGENT");
        jsonObjectTMS.put("address", "22 Road Festac");
        jsonObjectTMS.put("password", "ksali34A");
        jsonObjectTMS.put("password_confirmation", "ksali34A");

        String jsonBodyTMS = jsonObjectTMS.toString();

        OkHttpClient clientTMS = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, jsonBodyTMS);

        try{
            Request request = new Request.Builder()
                    .url("https://tms.coopvest.ng/api/v1/register")
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer 95|O2AHuCmpzRR1czooKEvvmJqBSYz4gA5qeXVhs3eZ")
                    .build();
            Response response = clientTMS.newCall(request).execute();

            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                System.out.println(responseBody.string());
            }
            response.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String byteArrayToHexString(byte[] byteArray) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : byteArray) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

}
