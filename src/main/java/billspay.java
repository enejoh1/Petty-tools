
import okhttp3.*;
import org.json.JSONObject;

import javax.print.attribute.standard.Media;
import java.util.Base64;

import java.io.IOException;

public class billspay{

    //validate account number v1 api
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
    static String accessToken() throws IOException {
        String url = "https://sandbox.interswitchng.com/passport/oauth/token";
        String grantBody = "grant_type=client_credentials";
        String clientID = "IKIA84F52B5A143FC6D79D99313D58485A2429A72915";
        String secretKey = "Urhy1Vg+3eAJsre+fxA+AB+umB42xAhl4FRytmeBtW8=";
        String accessTokenString = clientID + ":" + secretKey;
        byte[] accessTokenBytes = Base64.getEncoder().encode(accessTokenString.getBytes());
        String accessToken = new String(accessTokenBytes);
        System.out.println("Access Token "+accessToken);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

        RequestBody requestBody = RequestBody.create(grantBody, mediaType);
        Request request = new Request.Builder().url(url)
                .addHeader("Authorization", "Basic " + accessToken)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
        String responseBodyString = responseBody.string();
        JSONObject jsonObject = new JSONObject(responseBodyString);
        accessToken = jsonObject.getString("access_token");
        System.out.println(accessToken);
        response.close();
        return accessToken;
    }

    //get billers v5 api
    static void getBillers() throws IOException{
        String url1 = "http://172.25.20.59/quicktellerservice/api/v5/services"; //url that came with postman
        String url2 = "https://qa.interswitchng.com/quicktellerservice/api/v5/services";
        String bearerToken = accessToken();
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        RequestBody requestBody = RequestBody.create("", mediaType);
        Request request = new Request.Builder()
                .url(url1)
                .addHeader("Content-Type", "Application/json")
                .addHeader("Authorization", bearerToken)
                .addHeader("terminalID", "3DMO0001") //terminal id used in v2
                .method("GET", null)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();
        System.out.println(responseBody.string());
    }

    public static void main(String[] args) throws IOException {
//        validateNumber();
        getBillers();
    }
}