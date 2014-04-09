package com.magic.promotion.util;




import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: wangjc
 * Date: 2010-7-20
 * Time: 9:34:29
 */
public class EncryptUtil {

    public static String getEncryptString(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if(i<0) i+= 256;
                if(i<16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            //System.out.println("result: " + buf.toString());//32浣嶇殑鍔犲瘑
            //System.out.println("result: " + buf.toString().substring(8,24));//16浣嶇殑鍔犲瘑
            return buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes("UTF-8");
            //浣跨敤MD5鍒涘缓MessageDigest瀵硅薄
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte b = md[i];
                //System.out.println((int)b);
                //灏嗘病涓暟(int)b杩涜鍙屽瓧鑺傚姞瀵�
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {return null;}
    }

    public static void main(String[] args) {
        String a = "2010V鐨�.01#127.0.0.1ABCD1234";
        String b = EncryptUtil.MD5(a);
        String c = EncryptUtil.getEncryptString(b);
        String d = EncryptUtil.getEncryptString(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
    }
}
