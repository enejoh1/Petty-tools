
import okhttp3.*;
import org.json.JSONObject;
import java.util.Base64;

import java.io.IOException;

public class InterswitchHTTP{

    //validate account number
    static void validateNumber() throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        String url = "https://sandbox.interswitchng.com/api/v1/nameenquiry/banks/accounts/names";

        Request request = new Request.Builder().url(url)
                .addHeader("Authorization", "InterswitchAuth SUtJQTgzQkREMEI2NTlFMzUzQTI4OUQ1QUQ1QUQ5NzkzNjYwOERENzUwNzI= ")
                .addHeader("Content-Type", "application/json")
                .addHeader("Signature", "wlfvTG3PDGG9bWCO84MnAe+6A9A=")
                .addHeader("Timestamp", "1632302632")
                .addHeader("Nonce", "9875059b820d01abdccc55b3d2ef385f")
                .addHeader("SignatureMethod", "SHA1")
                .addHeader("TerminalID", "3DMO0001")
                .get()
                .build();
        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
        System.out.println(responseBody.string());
    }

    //generate access/bearer token
    static void accessToken() throws IOException {
        String url = "https://sandbox.interswitchng.com/passport/oauth/token";
        String jsonBody = "\"grant_type\": \"client_credentials\"";
        String clientID = "IKIA84F52B5A143FC6D79D99313D58485A2429A72915";
        String secretKey = "Urhy1Vg+3eAJsre+fxA+AB+umB42xAhl4FRytmeBtW8=";
        String accessTokenString = clientID + secretKey;
        byte[] accessTokenBytes = Base64.getEncoder().encode(accessTokenString.getBytes());
        String accessToken = new String(accessTokenBytes);
        System.out.println(accessToken);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        RequestBody requestBody = RequestBody.create(jsonBody, mediaType);
        Request request = new Request.Builder().url(url)
                .addHeader("Authorization", "Basic " + accessToken)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .method("POST", requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        int rcocde = response.code();
        ResponseBody responseBody = response.body();
        System.out.println("======"+responseBody.string());
        System.out.println(rcocde);
    }

    public static void main(String[] args) throws IOException {
//        validateNumber();
        accessToken();
    }
}