package com.github.aberdar.blockchain;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Block {

    // Хэш блока, рассчитаный на основе других данных
    private String hash;
    // Хэш предыдущего блока
    private String previousHash;
    // Фактические данные, любая информация, имеющая ценность
    private String data;
    // Временная метка создания блока
    private long timeStamp;
    // Произвольное число, используется в криптографии
    private int nonce;

    // Регистрация сообщений
    private static Logger logger = Logger.getLogger(Block.class.getName());

    public Block(String previousHash,
                 String data,
                 long timeStamp) {

        this.hash = calculationHashBlock();
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = timeStamp;
    }

    // Добыча блока
    public String blockMining(int index) {

        String indexString = new String(new char[index]).replace('\0', '0');
        while (!hash.substring(0, index).equals(indexString)) {
            nonce++;
            hash = calculationHashBlock();
        }
        return hash;
    }

    // Расчет хэша
    public String calculationHashBlock() {

        String dataToHash = previousHash
                + Long.toString(timeStamp)
                + Integer.toString(nonce)
                + data;
        MessageDigest messageDigest = null;

        byte[] bytes = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            bytes = messageDigest.digest(dataToHash.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }

        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    public String getHash() {
        return this.hash;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public void setData(String data) {
        this.data = data;
    }
}
