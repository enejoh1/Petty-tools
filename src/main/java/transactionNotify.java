import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class transactionNotify {

    public static void main(String[] args) {

        String personalImage = imageStorage.passport;
        String businessImage = imageStorage.businessImage;
        String licenseImage = imageStorage.licenseImage;
        String idCardImage = imageStorage.idCardImage;

        String channelId = "57acc41abfcb4161a285e53d9d745a59";
        String secret = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDy9Y6PkzPAqvvEiIZFxywiK9M5vd7RXe6YleQdBwL2rq930HbMdrD+3vn6LpW2cFlnTD90SUTo55m6akc+TLCg278VT+cTEFMeFGiPmQ1aVZ7UcN4fzvQ4WkzxgCpwc+xLusqSGcE3f3iOHZNcSddB7KHHdZkue/RJBXWuakpTWwIDAQAB";
        long time = System.currentTimeMillis();
        // String time = String.format("%010d", time2);

        System.out.println("Tome ---->"+time);
        String sign = getSign(time, channelId, secret);


        String jsonString = "{\n" +
                "    \"agentAmt\": \"1,990.00\",\n" +
                "    \"aggregatorAmt\": \"0.00\",\n" +
                "    \"createAt\": \"2024-04-02 11:28:18\",\n" +
                "    \"destAcct\": \"519899******8799\",\n" +
                "    \"fee\": \"10.00\",\n" +
                "    \"orderMsg\": \"SUCCESSFUL\",\n" +
                "    \"orderNo\": \"853254207080703734\",\n" +
                "    \"orderStatus\": \"00\",\n" +
                "    \"orderTypeId\": 2,\n" +
                "    \"origAmt\": \"2,000.00\",\n" +
                "    \"rrn\": \"641231628637\",\n" +
                "    \"sentAt\": \"2024-04-02 11:28:18\",\n" +
                "    \"serialNum\": \"98220514989068\",\n" +
                "    \"tid\": \"HOR-00004\",\n" +
                "    \"transAmt\": \"1,990.00\",\n" +
                "    \"transName\": \"CASHOUT\",\n" +
                "    \"updatedAt\": \"2024-04-02 11:28:18\",\n" +
                "    \"userName\": \"pretty\"\n" +
                "}";

        String s11 = JSONUtil.toJsonStr(jsonString);
        String encryptAES1 = CoopvestUtil.encryptAES(s11, CoopvestUtil.KEY, CoopvestUtil.IV);
        System.out.println("Webhook " +  encryptAES1);



        String bvnValue = "22145862752";


        String s = "{    " +
                "\"bvn\": \""+bvnValue+"\",    " +
                "\"firstname\": \"Enejoh\",    " +
                "\"lastname\": \"Abu\",    " +
                "\"phonenumber\": \"08067242034\",   " +
                " \"username\": \"08067242034\",    " +
                "\"password\": \"enejoh123\",    " +
                "\"email\": \"chrisenejohabu@yahoo.com\",    " +
                "\"dob\": \"1990-08-10\",   " +
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
                "\"serialnumber\": \"98230326906240\",    " +
                "\"aggregatorUserName\": \"upagg\"}";
        String s1 = JSONUtil.toJsonStr(s);
        String encryptAES = CoopvestUtil.encryptAES(s1, CoopvestUtil.KEY, CoopvestUtil.IV);

        Map<String, String> heard = Map.of("X-Tenantid", "9rapoint");
        String body1 = HttpUtil.createGet(String.format("https://wemaatom.test.horizonpay.cn/api/coopvest/state?channelId=%s&time=%s&sign=%s", channelId, time, sign))
                .headerMap(heard, false)
                .execute().body();
        System.out.println("body = " + body1);

        System.out.println("Webhook---------------------------------------------> " +  encryptAES1);
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
