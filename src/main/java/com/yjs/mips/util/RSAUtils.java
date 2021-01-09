package com.yjs.mips.util;



//        import com.mirana.frame.utils.log.LogUtils;
        import org.apache.commons.codec.binary.Base64;

        import javax.crypto.BadPaddingException;
        import javax.crypto.Cipher;
        import javax.crypto.IllegalBlockSizeException;
        import javax.crypto.NoSuchPaddingException;
        import java.nio.charset.Charset;
        import java.nio.charset.StandardCharsets;
        import java.security.*;
        import java.security.spec.InvalidKeySpecException;
        import java.security.spec.PKCS8EncodedKeySpec;
        import java.security.spec.X509EncodedKeySpec;

/**
 * Title：RSA加解密工具
 *
 * @CreatedBy Mirana
 * @DateTime 2018/3/2315:30
 */

public class RSAUtils {

    // 字符编码格式
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    // 换行
    public static final String  NEXTLINE        = System.getProperty("line.separator");
    // RSA 算法，Java默认"RSA"="RSA/ECB/PKCS1Padding"
    public static final String  ALGORITHM_RSA   = "RSA";

    /**
     * 生成指定长度的keypair，最小长度512，目前1024的长度已经很难破解
     *
     * @param keysize 秘钥对长度
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair genKeyPair (int keysize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        keyPairGenerator.initialize(keysize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 公钥加密，默认UTF-8
     *
     * @param contentBytes 要加密的Bytes
     * @param publickey    公钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static byte[] encrypt (byte[] contentBytes, PublicKey publickey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

        Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publickey);
        byte[] encryptBytes = cipher.doFinal(contentBytes);
        encryptBytes = Base64.encodeBase64(encryptBytes);
        return encryptBytes;
    }

    /**
     * 公钥加密字符串
     *
     * @param content            要加密的文本
     * @param base64PublickeyStr base64编码后的公钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static String encryptStr (String content, String base64PublickeyStr)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

        // publickeyStr-->PublicKey
        byte[] buffer = Base64.decodeBase64(base64PublickeyStr.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        PublicKey publickey = keyFactory.generatePublic(keySpec);

        return new String(encrypt(content.getBytes(DEFAULT_CHARSET), publickey), DEFAULT_CHARSET);
    }

    /**
     * 私钥解密
     *
     * @param contentBytes 要解密的Bytes
     * @param privatekey   私钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static byte[] decrypt (byte[] contentBytes, PrivateKey privatekey)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
        cipher.init(Cipher.DECRYPT_MODE, privatekey);
        return cipher.doFinal(Base64.decodeBase64(contentBytes));
    }

    /**
     * 私钥解密字符串，默认UTF-8
     *
     * @param content             要解密的文本
     * @param base64PrivatekeyStr Base64编码后的私钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeySpecException
     */
    public static String decryptStr (String content, String base64PrivatekeyStr)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {

        // privatekeyStr-->PrivateKey
        byte[] buffer = Base64.decodeBase64(base64PrivatekeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
        PrivateKey privatekey = keyFactory.generatePrivate(keySpec);

        return new String(decrypt(content.getBytes(DEFAULT_CHARSET), privatekey), DEFAULT_CHARSET);
    }

    /**
     * RAS加密
     *
     * @param content            原文本
     * @param base64PublickeyStr base64编码后的公钥
     * @return
     */
    public static String rsaEncrypt (String content, String base64PublickeyStr) {
        StringBuffer rsaLogStr = new StringBuffer();
        rsaLogStr.append(NEXTLINE);
        rsaLogStr.append("----- [ RSA加密 ] Start -----" + NEXTLINE);
        rsaLogStr.append("----- [ RSA加密 ] 加密前文本：" + content + NEXTLINE);
        rsaLogStr.append("----- [ RSA加密 ] 公钥：" + base64PublickeyStr + NEXTLINE);

        try {
            content = encryptStr(content, base64PublickeyStr);
            rsaLogStr.append("----- [ RSA加密 ] 加密后文本：" + content + NEXTLINE);
            rsaLogStr.append("----- [ RSA加密 ] End -----" + NEXTLINE);
//            LogUtils.info(rsaLogStr);
        } catch (Exception e) {
            rsaLogStr.append("----- [ RSA加密 ] 加密时发生异常：" + e.toString() + NEXTLINE);
            rsaLogStr.append("----- [ RSA加密 ] End -----" + NEXTLINE);
//            LogUtils.error(rsaLogStr);
            e.printStackTrace();
        }

        return content;
    }

    /**
     * RSA解密
     *
     * @param content             要解密的文本
     * @param base64PrivatekeyStr Base64编码后的私钥
     * @return
     */
    public static String rsaDecrypt (String content, String base64PrivatekeyStr) {
        StringBuffer rsaLogStr = new StringBuffer();
        rsaLogStr.append(NEXTLINE);
        rsaLogStr.append("----- [ RSA解密 ] Start -----" + NEXTLINE);
        rsaLogStr.append("----- [ RSA解密 ] 解密前文本：" + content + NEXTLINE);
        rsaLogStr.append("----- [ RSA解密 ] 私钥：" + base64PrivatekeyStr + NEXTLINE);

        try {
            content = decryptStr(content, base64PrivatekeyStr);
            rsaLogStr.append("----- [ RSA解密 ] 解密后文本：" + content + NEXTLINE);
            rsaLogStr.append("----- [ RSA解密 ] End -----" + NEXTLINE);
//            LogUtils.info(rsaLogStr);
        } catch (Exception e) {
            rsaLogStr.append("----- [ RSA解密 ] 解密时发生异常：" + e.toString() + NEXTLINE);
            rsaLogStr.append("----- [ RSA解密 ] End -----" + NEXTLINE);
//            LogUtils.error(rsaLogStr);
            e.printStackTrace();
        }

        return content;
    }

//    public static void main (String[] args) {
//        KeyPair keypair = null;
//        try {
//            keypair = genKeyPair(512);// 公钥私钥对
//        } catch (Exception e) {
////            LogUtils.error("生成公钥私钥发生异常：" + e.toString());
//        }
//        PublicKey publickey = keypair.getPublic();// 公钥
//        PrivateKey privatekey = keypair.getPrivate();// 私钥
//
//        // 原文本
//        String content = "{'name':'mmmirana','age':'27','phone':'17112345678'}";
//
//        // base64编码后的公钥（一般从文件id_ras.pub中读取）
//        String base64PublickeyStr = Base64.encodeBase64String(publickey.getEncoded());
//        // base64编码后的私钥（一般从文件id_rsa中读取）
//        String base64PrivatekeyStr = Base64.encodeBase64String(privatekey.getEncoded());
//
//        // RSA加密
//        String encryptedContent = rsaEncrypt(content, base64PublickeyStr);
//
//        // RSA解密
//        rsaDecrypt(encryptedContent, base64PrivatekeyStr);
//    }
}