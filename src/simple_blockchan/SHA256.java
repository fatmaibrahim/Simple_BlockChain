package simple_blockchan;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SHA256 {

//    public static String generateHash(String value) {
//        String hash = null;
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            byte[] bytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < bytes.length; i++) {
//                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
//            }
//            hash = sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return hash;
//    }


    private static Map<String, String> digiMap = new HashMap<>();

    static {
        digiMap.put("0", "0000");
        digiMap.put("1", "0001");
        digiMap.put("2", "0010");
        digiMap.put("3", "0011");
        digiMap.put("4", "0100");
        digiMap.put("5", "0101");
        digiMap.put("6", "0110");
        digiMap.put("7", "0111");
        digiMap.put("8", "1000");
        digiMap.put("9", "1001");
        digiMap.put("a", "1010");
        digiMap.put("b", "1011");
        digiMap.put("c", "1100");
        digiMap.put("d", "1101");
        digiMap.put("e", "1110");
        digiMap.put("f", "1111");
    }

    public static String hexToBin(String s) {
        char[] hex = s.toCharArray();
        String binaryString = "";
        for (char h : hex) {
            binaryString = binaryString + digiMap.get(String.valueOf(h));
        }
        return binaryString;
    }

    public static byte[] getSHA(String input)
    {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] bytes)
    {
//        BigInteger number = new BigInteger(1, hash);
//        StringBuilder hexString = new StringBuilder(number.toString(16));
//        while (hexString.length() < 32)
//            hexString.insert(0, '0');
//        return hexString.toString();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
