package com.imfbp.boot.common.codec.threedes;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

/**
 * SecretUtils {3DES加密解密的工具类 }
 * 
 * @author William
 * @date 2013-04-19
 */
public class ThreeDES {

	// 定义加密算法，有DES、DESede(即3DES)、Blowfish
	private static final String Algorithm = "DESede";
	private static final String CRYPT_KEY = "sdfewrwoippkvcxsamfsfsd;lkewpriewpirpewfdsfsd";
	private static final String DEFAULT_ENCOD = "UTF-8";

	public static String encrypt(String src) {
		try {
			byte[] srcByte = src.getBytes(DEFAULT_ENCOD);
			byte[] encryptByte = encryptMode(srcByte, DEFAULT_ENCOD, CRYPT_KEY);
			return Base64.encodeBase64String(encryptByte);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encrypt(String src, String cryptKey) {
		try {
			byte[] srcByte = src.getBytes(DEFAULT_ENCOD);
			byte[] encryptByte = encryptMode(srcByte, DEFAULT_ENCOD, cryptKey);
			return Base64.encodeBase64String(encryptByte);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encrypt(String src, String encode, String cryptKey) {
		try {
			byte[] srcByte = src.getBytes(encode);
			byte[] encryptByte = encryptMode(srcByte, encode, cryptKey);
			return Base64.encodeBase64String(encryptByte);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String decrypt(String src) {
		try {
			byte[] srcByte = src.getBytes(DEFAULT_ENCOD);
			byte[] baseByte = Base64.decodeBase64(srcByte);
			byte[] decryptByte = decryptMode(baseByte, DEFAULT_ENCOD, CRYPT_KEY);
			return new String(decryptByte, DEFAULT_ENCOD);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String decrypt(String src, String cryptKey) {
		try {
			byte[] srcByte = src.getBytes(DEFAULT_ENCOD);
			byte[] baseByte = Base64.decodeBase64(srcByte);
			byte[] decryptByte = decryptMode(baseByte, DEFAULT_ENCOD, cryptKey);
			return new String(decryptByte, DEFAULT_ENCOD);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String decrypt(String src, String encode, String cryptKey) {
		try {
			byte[] srcByte = src.getBytes(encode);
			byte[] baseByte = Base64.decodeBase64(srcByte);
			byte[] decryptByte = decryptMode(baseByte, encode, cryptKey);
			return new String(decryptByte, encode);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	private static byte[] encryptMode(byte[] src, String encode, String key) {
		try {
			String cryptKey = StringUtils.isNotBlank(key) ? key : CRYPT_KEY;
			SecretKey deskey = new SecretKeySpec(build3DesKey(cryptKey, encode), Algorithm); // 生成密钥
			Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
			c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	private static byte[] decryptMode(byte[] src, String encode, String key) {
		try {
			String cryptKey = StringUtils.isNotBlank(key) ? key : CRYPT_KEY;
			SecretKey deskey = new SecretKeySpec(build3DesKey(cryptKey, encode), Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey); // 初始化为解密模式
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (java.lang.Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}

	/*
	 * 根据字符串生成密钥字节数组
	 * @param keyStr 密钥字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] build3DesKey(String keyStr, String encode) throws UnsupportedEncodingException {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0

		encode = StringUtils.isNotBlank(encode) ? encode : DEFAULT_ENCOD;
		byte[] temp = keyStr.getBytes(encode); // 将字符串转成字节数组
		
		//执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		if (key.length > temp.length) {
			// 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			// 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String msg = "3DES加密解密案例asasasasas";
		System.out.println("【加密前】：" + msg);
		// 加密
		String secretArr = ThreeDES.encrypt(msg,"UTF-8","aaasadsfawfsfsfsfsfa");
		System.out.println(secretArr);
		// 解密
		String myMsgArr = ThreeDES.decrypt(secretArr,"UTF-8","aaasadsfawfsfsfsfsfa");
		System.out.println("【解密后】：" + new String(myMsgArr));
		System.out.println("w4HMH458ncRbHXL+gycOd/ZdMyeyzh7C3db1TrMtSX3d1vVOsy1Jfd3W9U6zLUl90+gIZA3VRxM=".length());
	}
}