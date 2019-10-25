/*
 * @(#)DigestUtils.java Time: 2013-8-19
 *
 * Copyright 2013 skyedu.xuedou.com All rights reserved.
 */

package com.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.util.StringUtils;

/**
 *<pre>类说明</pre>
 *<b>功能描述：</b>
 * 在这里具体描述功能内容...
 * 注意事项：
 * 在这里具体描述注意事项，如果没有注意事项,请删除注意事项条目。
 * @author  xj.chen  email:xj_chen_cq@163.com
 * @version 1.0, 2013-8-19
 */
public class DigestUtils {  
	  
    /** 
     * 根据给定摘要算法创建一个消息摘要实例 
     *  
     * @param algorithm 
     *            摘要算法名 
     * @return 消息摘要实例 
     * @see MessageDigest#getInstance(String) 
     * @throws RuntimeException 
     *             当{@link java.security.NoSuchAlgorithmException} 发生时 
     */  
    static MessageDigest getDigest(String algorithm) {  
        try {  
            return MessageDigest.getInstance(algorithm);  
        } catch (NoSuchAlgorithmException e) {  
            throw new RuntimeException(e.getMessage());  
        }  
    }  
  
    /** 
     * 获取MD5 消息摘要实例 
     *  
     * @return MD5 消息摘要实例 
     * @throws RuntimeException 
     *             当{@link java.security.NoSuchAlgorithmException} 发生时 
     */  
    private static MessageDigest getMd5Digest() {  
        return getDigest("MD5");  
    }  
  
    /** 
     * 获取SHA-1 消息摘要实例 
     *  
     * @return SHA-1 消息摘要实例 
     * @throws RuntimeException 
     *             当{@link java.security.NoSuchAlgorithmException} 发生时 
     */  
    private static MessageDigest getShaDigest() {  
        return getDigest("SHA");  
    }  
  
    /** 
     * 获取SHA-256 消息摘要实例 
     *  
     * @return SHA-256 消息摘要实例 
     * @throws RuntimeException 
     *             当{@link java.security.NoSuchAlgorithmException} 发生时 
     */  
    private static MessageDigest getSha256Digest() {  
        return getDigest("SHA-256");  
    }  
  
    /** 
     * 获取SHA-384 消息摘要实例 
     *  
     * @return SHA-384 消息摘要实例 
     * @throws RuntimeException 
     *             当{@link java.security.NoSuchAlgorithmException} 发生时 
     */  
    private static MessageDigest getSha384Digest() {  
        return getDigest("SHA-384");  
    }  
  
    /** 
     * 获取SHA-512 消息摘要实例 
     *  
     * @return SHA-512 消息摘要实例 
     * @throws RuntimeException 
     *             当{@link java.security.NoSuchAlgorithmException} 发生时 
     */  
    private static MessageDigest getSha512Digest() {  
        return getDigest("SHA-512");  
    }  
  
    /** 
     * 使用MD5消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return 消息摘要（长度为16的字节数组） 
     */  
    public static byte[] encodeMD5(byte[] data) {  
        return getMd5Digest().digest(data);  
    }  
  
    /** 
     * 使用MD5消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return 消息摘要（长度为32的十六进制字符串） 
     */  
    public static String encodeMD5Hex(byte[] data) {  
        return Hex.encodeHexString(encodeMD5(data));  
    }  
  
    /** 
     * 使用SHA-1消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return SHA-1消息摘要（长度为20的字节数组） 
     */  
    public static byte[] encodeSHA(byte[] data) {  
        return getShaDigest().digest(data);  
    }  
  
    /** 
     * 使用SHA-1消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return SHA-1消息摘要（长度为40的十六进制字符串） 
     */  
    public static String encodeSHAHex(byte[] data) {  
        return Hex.encodeHexString(getShaDigest().digest(data));  
    }  
  
    /** 
     * 使用SHA-256消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return SHA-256消息摘要（长度为32的字节数组） 
     */  
    public static byte[] encodeSHA256(byte[] data) {  
        return getSha256Digest().digest(data);  
    }  
  
    /** 
     * 使用SHA-256消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return SHA-256消息摘要（长度为64的十六进制字符串） 
     */  
    public static String encodeSHA256Hex(byte[] data) {  
        return Hex.encodeHexString(encodeSHA256(data));  
    }  
  
    /** 
     * 使用SHA-384消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return SHA-384消息摘要（长度为43的字节数组） 
     */  
    public static byte[] encodeSHA384(byte[] data) {  
        return getSha384Digest().digest(data);  
    }  
  
    /** 
     * 使用SHA-384消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return SHA-384消息摘要（长度为86的十六进制字符串） 
     */  
    public static String encodeSHA384Hex(byte[] data) {  
        return Hex.encodeHexString(encodeSHA384(data));  
    }  
  
    /** 
     * 使用SHA-512消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return SHA-512消息摘要（长度为64的字节数组） 
     */  
    public static byte[] encodeSHA512(byte[] data) {  
        return getSha512Digest().digest(data);  
    }  
  
    /** 
     * 使用SHA-512消息摘要算法计算消息摘要 
     *  
     * @param data 
     *            做消息摘要的数据 
     * @return SHA-512消息摘要（长度为128的十六进制字符串） 
     */  
    public static String encodeSHA512Hex(byte[] data) {  
        return Hex.encodeHexString(encodeSHA512(data));  
    }  
    
    /**
	 * 六位随机数
	 * @return
	 */
	public static int getRandom(){
		Random rand = new Random();
		return rand.nextInt(999999);
	}
    
	/**
	 * 32位随机码
	 */
	public static String getCode() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");  
	}
	
    /**
	 * 密码种子，6位16进制字符
	 * @return
	 */
	public static String getSeeds() {
		Random random = new Random();
		String result = Integer
				.toHexString(random.nextInt(0x1000000) + 0x1000000);
		return result.substring(1);
	}
    
	/**
	 * BAS64密码种子，6位16进制字符
	 * @return
	 */
	public static String getSeedsBeas64() {
		Random random = new Random();
		String result = Integer
				.toHexString(random.nextInt(0x1000000) + 0x1000000);
		try {
			return Base64.encodeBase64String(result.substring(1).getBytes("UnicodeLittleUnmarked"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result.substring(1);
	}
    
	/**
	 * SHA1加密
	 * @param psw 密文
	 * @param seeds 密码种, 建议使用BAS64密码种
	 * @return
	 */
	public static String hexSha1(String psw, String seeds){
		byte[] codes = null, passwords = null;
		try {
			codes = Base64.decodeBase64(seeds.getBytes("UnicodeLittleUnmarked"));
			passwords = psw.getBytes("UnicodeLittleUnmarked");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	byte[] newArray = new byte[codes.length+passwords.length];
    	System.arraycopy(codes,0,newArray,0,codes.length);
    	System.arraycopy(passwords,0,newArray,codes.length,passwords.length);
    	byte[] digest = DigestUtils.encodeSHA(newArray);
        return Base64.encodeBase64String(digest);
	}
	
	/**
	 * 判断密文是否匹配
	 * @param psw 密文
	 * @param seeds 密码种
	 * @param dbPsw 对比密文
	 * @return
	 */
	public static boolean isEmpty(String psw, String seeds, String dbPsw){
		if (StringUtils.isEmpty(psw)||StringUtils.isEmpty(seeds)||StringUtils.isEmpty(dbPsw)) {
			return false;
		}
		return hexSha1(psw, seeds).equals(dbPsw);
	}
    public static void main(String[] args){
    	String seed = "saPAvIWFgopvsqIAQdLADg==";
    	String pas = hexSha1("666666", seed);
    	System.out.println("密码种"+seed + "密码："+pas);
    	System.out.println(isEmpty("666666", seed, "JWl+fTHsZvgt1hSg+PbFT52kMUk="));
        //System.out.println("加密结果beas64结果："+hexSha1("666666", getSeedsBeas64()));
    }
}