package cn.edu.hdu.lab505.innovation.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by hhx on 2016/11/19.
 */
public class ByteUtil {

    public static float toFloat(byte[] b) {
        ByteBuffer buf = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN);
        return buf.getFloat();
    }
    public static String byteToString(byte[] a) {
        StringBuffer sb = new StringBuffer();
        for (Byte b : a) {
            int number = b & 0xff;
            String hex = Integer.toHexString(number);
            if (hex.length() == 1) {
                sb.append("0" + hex);
            } else {
                sb.append(hex);
            }
        }
        return sb.toString().toUpperCase();
    }

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return b;
    }
}
