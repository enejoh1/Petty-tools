

import cn.hutool.core.codec.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Tony
 * @create 2024-04-02
 */
public class CoopvestUtil {

    public static final String KEY = ")KCSWITHC%^$$%@H";

    public static final String IV = "#$%#^%KCSWITC945";

    public static String encryptAES(String plainText, String key, String iv) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(addZeroPadding(key.getBytes("UTF-8"), 16), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] bytes = cipher.doFinal(plainText.getBytes("UTF-8"));
            return Base64.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decryptAES(String encryptedStr, String key, String iv) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(addZeroPadding(key.getBytes("UTF-8"), 16), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] bytes = cipher.doFinal(Base64.decode(encryptedStr));

            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //添加ZeroPadding，使消息长度对齐到 blockSize 的倍数
    private static byte[] addZeroPadding(byte[] data, int blockSize) {
        int paddingLength = blockSize - (data.length % blockSize);
        byte[] paddedData = new byte[data.length + paddingLength];
        System.arraycopy(data, 0, paddedData, 0, data.length);
        return paddedData;
    }
}
