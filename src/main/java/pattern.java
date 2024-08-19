import java.io.FileNotFoundException;

public class pattern {

    public static void main(String[] args) {
        String channelId =  "ashdcgu", secret= "1234", time= "77t8", version="uhqwys";
        String signStr = String.format("channelId=%s,secret=%s,time=%s,version=%s", channelId, secret, time,version);
        System.out.println(signStr);
    }


}
