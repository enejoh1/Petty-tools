import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Demo {

    public static void main(String[] args) {

        String personalImage = imageStorage.passport;
        String businessImage = imageStorage.businessImage;
        String licenseImage = imageStorage.licenseImage;
        String idCardImage = imageStorage.idCardImage;


        String channelId = "57acc41abfcb4161a285e53d9d745a59";
        String secret = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDy9Y6PkzPAqvvEiIZFxywiK9M5vd7RXe6YleQdBwL2rq930HbMdrD+3vn6LpW2cFlnTD90SUTo55m6akc+TLCg278VT+cTEFMeFGiPmQ1aVZ7UcN4fzvQ4WkzxgCpwc+xLusqSGcE3f3iOHZNcSddB7KHHdZkue/RJBXWuakpTWwIDAQAB";
       // long time = System.currentTimeMillis();
        long time = Long.parseLong("1719148851216");

        System.out.println("TimeStamp  "+time);

        String sign = getSign(time, channelId, secret);
        System.out.println("SIGNATURE  ---->  "+sign);
        String bvnValue = "22145862752";


        String s = "{    " +
                "\"bvn\": \""+bvnValue+"\",    " +
                "\"firstname\": \"Enejoh\",    " +
                "\"lastname\": \"Abu\",    " +
                "\"phonenumber\": \"09071529730\",   " +
                " \"username\": \"09071529730\",    " +
                "\"password\": \"test@22A\",    " +
                "\"email\": \"abuchristopherenejoh@gmail.com\",    " +
                "\"dob\": \"1989-08-14\",   " +
                " \"nin\": \"69165188083\",    " +
                "\"state\": \"Oyo State\",    " +
                "\"lga\": \"Ibadan North\",    " +
                "\"gender\": \"Male\",   " +
                "\"cacnumber\": \"null\",   " +
                "\"streetname\": \"No 16 Areetedo street, New bodija\",    " +
                "\"businesstype\": \"other\",   " +
                " \"businessname\": \"coopvest\",    " +
                "\"businessaddress\": \"No 16 Areetedo street, New bodija\",    " +
                "\"personalImage\": \""+personalImage+"\",   " +
                " \"businessImage\": \""+businessImage+"\",    " +
                "\"licenceImage\": \""+licenseImage+"\",    " +
                "\"idCardImage\": \""+idCardImage+"\",    " +
                "\"serialnumber\": \"T502310100004746\",    " +
                "\"aggregatorUserName\": \"cao@coopvest.ng\"}";
        String s1 = JSONUtil.toJsonStr(s);
        String encryptAES = CoopvestUtil.encryptAES(s1, CoopvestUtil.KEY, CoopvestUtil.IV);
        //System.out.println(encryptAES);


        Map<String, String> requestMap = Map.of("request", encryptAES);
        String request = JSON.toJSONString(requestMap);
        Map<String, String> heard = Map.of("X-Tenantid", "9rapoint");
        String body = HttpUtil.createPost(String.format("https://atomm-staging.9rapoint.com/api/coopvest/register?channelId=%s&time=%s&sign=%s", channelId, time, sign))
                .headerMap(heard, false)
                .body(request).execute().body();
        System.out.println("body = " + body);
    }

    private static String getSign(long time, String channelId, String secret) {
        String signStr = String.format("channelId=%s,secret=%s,time=%s,version=%s", channelId, secret, time, "1.0.0");
        // Guava package util
        System.out.println("Signstr---- "+signStr);
        String sign = Hashing.sha256().hashString(signStr, StandardCharsets.UTF_8).toString();
        // Hutool package util
        // String sign = SecureUtil.sha256(signStr);
        System.out.println(sign);
        return sign;
    }
}
