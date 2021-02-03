package io.github.xf_zhjnc.tangyuantv.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {

    private static byte[] md5(byte[] str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str);
        return md.digest();
    }

    private static byte[] getHmacMd5Bytes(byte[] key, byte[] data) throws NoSuchAlgorithmException {
        byte[] ipad = new byte[64];
        byte[] opad = new byte[64];
        for (int i = 0; i < 64; i++) {
            ipad[i] = 54;
            opad[i] = 92;
        }
        byte[] actualKey = key;
        byte[] keyArr = new byte[64];
        if (key.length > 64) {
            actualKey = md5(key);
        }
        for (int i2 = 0; i2 < actualKey.length; i2++) {
            keyArr[i2] = actualKey[i2];
        }
        if (actualKey.length < 64) {
            for (int i3 = actualKey.length; i3 < keyArr.length; i3++) {
                keyArr[i3] = 0;
            }
        }
        byte[] kIpadXorResult = new byte[64];
        for (int i4 = 0; i4 < 64; i4++) {
            kIpadXorResult[i4] = (byte) (keyArr[i4] ^ ipad[i4]);
        }
        byte[] firstAppendResult = new byte[(kIpadXorResult.length + data.length)];
        for (int i5 = 0; i5 < kIpadXorResult.length; i5++) {
            firstAppendResult[i5] = kIpadXorResult[i5];
        }
        for (int i6 = 0; i6 < data.length; i6++) {
            firstAppendResult[keyArr.length + i6] = data[i6];
        }
        byte[] firstHashResult = md5(firstAppendResult);
        byte[] kOpadXorResult = new byte[64];
        for (int i7 = 0; i7 < 64; i7++) {
            kOpadXorResult[i7] = (byte) (keyArr[i7] ^ opad[i7]);
        }
        byte[] secondAppendResult = new byte[(kOpadXorResult.length + firstHashResult.length)];
        for (int i8 = 0; i8 < kOpadXorResult.length; i8++) {
            secondAppendResult[i8] = kOpadXorResult[i8];
        }
        for (int i9 = 0; i9 < firstHashResult.length; i9++) {
            secondAppendResult[keyArr.length + i9] = firstHashResult[i9];
        }
        return md5(secondAppendResult);
    }

    public static String getHmacMd5Str(String key, String data) {
        try {
            byte[] hmacMd5Byte = getHmacMd5Bytes(key.getBytes("UTF-8"), data.getBytes("UTF-8"));
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < hmacMd5Byte.length; i++) {
                if (Integer.toHexString(hmacMd5Byte[i] & 255).length() == 1) {
                    md5StrBuff.append("0").append(Integer.toHexString(hmacMd5Byte[i] & 255));
                } else {
                    md5StrBuff.append(Integer.toHexString(hmacMd5Byte[i] & 255));
                }
            }
            return md5StrBuff.toString().toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }

}
