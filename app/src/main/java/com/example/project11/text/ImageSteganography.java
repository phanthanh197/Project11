package com.example.project11.text;

import android.graphics.Bitmap;

import com.example.project11.utils.Utility;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class ImageSteganography {
    private String message;
    private String secret_key;
    private String encrypted_message;
    private Bitmap image;
    private Bitmap encoded_image;
    private byte[] encrypted_zip;
    private Boolean encoded;
    private Boolean decoded;
    private Boolean secretKeyWrong;

    public ImageSteganography() {
        this.encoded = false;
        this.decoded = false;
        this.secretKeyWrong = true;
        this.message = "";
        this.secret_key = "";
        this.encrypted_message = "";//nơi lưu tin nhắn đc mã hóa
        this.image = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);
        this.encoded_image = Bitmap.createBitmap(600, 600, Bitmap.Config.ARGB_8888);//lới lưu ảnh đã đc ẩn tin nhắn mã hóa
        this.encrypted_zip = new byte[0];
    }

    public ImageSteganography(String message, String secret_key, Bitmap image) {

        this.message = message;
        //tạo key với 16 kí tự
        this.secret_key = convertKeyTo128bit(secret_key);
        this.image = image;
        // lấy mảng byte của tin nhắn
        this.encrypted_zip = message.getBytes();
        //mã hóa tin nhắn
        this.encrypted_message = encryptMessage(message, this.secret_key);
        this.encoded_image = Bitmap.createBitmap(1200, 900, Bitmap.Config.ARGB_8888);
        this.encoded = false;
        this.decoded = false;
        this.secretKeyWrong = true;
    }

    public ImageSteganography(String secret_key, Bitmap image) {
        this.secret_key = convertKeyTo128bit(secret_key);// tạo mật khẩu 128bit dữ liệu
        this.image = image;
        this.encoded = false;
        this.decoded = false;
        this.secretKeyWrong = true;
        this.message = "";
        this.encrypted_message = "";
        this.encoded_image = Bitmap.createBitmap(1200, 900, Bitmap.Config.ARGB_8888);
        this.encrypted_zip = new byte[0];
    }

    private static String encryptMessage(String message, String secret_key) {

        String encrypted_message = "";
        if (message != null) {
            if (!Utility.isStringEmpty(secret_key)) {
                try {
                    // Creating key and cipher
                    SecretKeySpec aesKey = new SecretKeySpec(secret_key.getBytes(), "AES");
                    Cipher cipher;
                    //AES cipher
                    cipher = Cipher.getInstance("AES");
                    // encrypt the text
                    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                    //Mã hóa dựa trên mật khẩu AES 128-bit (cipher.doFinal(message.getBytes()))
                    encrypted_message = android.util.Base64.encodeToString(cipher.doFinal(message.getBytes()), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                encrypted_message = message;
            }
        }
        //trả về dữ liêu
        return encrypted_message;
    }

    public static String decryptMessage(String message, String secret_key) {
        String decrypted_message = "";
        if (message != null) {
            if (!Utility.isStringEmpty(secret_key)) {
                try {
                    SecretKeySpec aesKey = new SecretKeySpec(secret_key.getBytes(), "AES");
                    Cipher cipher;

                    //AES cipher
                    cipher = Cipher.getInstance("AES");

                    // decrypting the text
                    cipher.init(Cipher.DECRYPT_MODE, aesKey);
                    String decrypted;
                    byte[] decoded;
                    decoded = android.util.Base64.decode(message.getBytes(), 0);
                    decrypted = new String(cipher.doFinal(decoded));

                    //returning decrypted text
                    return decrypted;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                decrypted_message = message;
            }
        }

        return decrypted_message;
    }

    private static String convertKeyTo128bit(String secret_key) {

        StringBuilder result = new StringBuilder(secret_key);

        if (secret_key.length() <= 16) {
            for (int i = 0; i < (16 - secret_key.length()); i++) {
                result.append("#");
            }
        } else {
            result = new StringBuilder(result.substring(0, 15));
        }
        return result.toString();
    }

    public Bitmap getEncoded_image() {
        return encoded_image;
    }

    public void setEncoded_image(Bitmap encoded_image) {
        this.encoded_image = encoded_image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public byte[] getEncrypted_zip() {
        return encrypted_zip;
    }

    public void setEncrypted_zip(byte[] encrypted_zip) {
        this.encrypted_zip = encrypted_zip;
    }

    public String getEncrypted_message() {
        return encrypted_message;
    }

    public void setEncrypted_message(String encrypted_message) {
        this.encrypted_message = encrypted_message;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Boolean isEncoded() {
        return encoded;
    }

    public void setEncoded(Boolean encoded) {
        this.encoded = encoded;
    }

    public Boolean isDecoded() {
        return decoded;
    }

    public void setDecoded(Boolean decoded) {
        this.decoded = decoded;
    }

    public Boolean isSecretKeyWrong() {
        return secretKeyWrong;
    }

    public void setSecretKeyWrong(Boolean secretKeyWrong) {
        this.secretKeyWrong = secretKeyWrong;
    }
}
