package com.ibh.pocketpassword.helper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CryptHelper {

	private static final Logger LOG = LoggerFactory.getLogger(CryptHelper.class);

	private static final int keyLength = 256;
	private static final int saltLength = keyLength / 8;
	private static final int iteration = 65536;
	private static String key;
	
	private CryptHelper() {
	}

//	private static byte[] keyByte;
//
//	public static void init(String key) {
//		final int maxlength = 24;
//		byte[] tempbyte = key.getBytes(StandardCharsets.UTF_8);
//		byte[] pwdbyte = new byte[maxlength];
//		if (tempbyte.length < maxlength) {
//			int pwdidx = 0;
//			int tempidx = 0;
//			while (pwdidx < maxlength) {
//				if (tempidx < tempbyte.length) {
//					pwdbyte[pwdidx++] = tempbyte[tempidx++];
//				} else {
//					tempidx = 0;
//				}
//			}
//		} else if (tempbyte.length > maxlength) {
//			pwdbyte = Arrays.copyOf(tempbyte, maxlength);
//		} else {
//			pwdbyte = tempbyte;
//		}
//		keyByte = pwdbyte;
//	}

	public static void init(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		key = hash(password);
	}
	
	public static String hash(String stringToHash) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
		messageDigest.update(stringToHash.getBytes(StandardCharsets.UTF_8));
		String hashedString = Base64.getEncoder().encodeToString(messageDigest.digest());
		return hashedString;
	}

	public static String encrypt(String cleartext) {
		try {

	        byte[] salt = new SecureRandom().generateSeed(saltLength);
	        PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt, iteration, keyLength);
	
	        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        Key passwordKey = secretKeyFactory.generateSecret(keySpec);
	        SecretKeySpec skey = new SecretKeySpec(passwordKey.getEncoded(), "AES");
	
	        byte[] iv = new byte[16]; 
	        new SecureRandom().nextBytes(iv);
	        final IvParameterSpec ivSpec = new IvParameterSpec(iv);
	
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, skey, ivSpec);
	        byte[] cipherText = cipher.doFinal(cleartext.getBytes());
	        
//	        System.out.println(toHexString(salt));
//	        System.out.println(toHexString(iv));
//	        System.out.println(toHexString(cipherText));
	        
	        return toHexString(salt) + toHexString(iv) + toHexString(cipherText);
		} catch(InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeySpecException ex) {
			LOG.warn("encrypt exception", ex);
		}
		return null;
	}
	
	public static String decrypt(String decryptText) {
		try {
			String salthex = decryptText.substring(0, 64);
			String ivhex = decryptText.substring(64, 96);
			String decrypthex = decryptText.substring(96);
	        
//			System.out.println(salthex);
//	        System.out.println(ivhex);
//	        System.out.println(decrypthex);
	        
			byte[] salt = toByteArray(salthex);
			byte[] iv = toByteArray(ivhex);
			byte[] decrypt = toByteArray(decrypthex);
			
	        PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray(), salt, iteration, keyLength);
	    	
	        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	        Key passwordKey = secretKeyFactory.generateSecret(keySpec);
	        SecretKeySpec skey = new SecretKeySpec(passwordKey.getEncoded(), "AES");
	        
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(iv));
	        byte[] plainText = cipher.doFinal(decrypt);
	        
	        return new String(plainText);
		}
		catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException ex) {
			LOG.warn("decrypt exception", ex);
		}
		return null;
	}
	
//	public static String encrypt1(final String clearText) {
//		KeyGenerator generator;
//		try {
//			generator = KeyGenerator.getInstance("AES");
//
//			generator.init(192);
//			Key key = generator.generateKey();
//
//			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
//			byte[] random = new byte[16];
//			secureRandom.nextBytes(random);
//			IvParameterSpec ivSpec = new IvParameterSpec(random);
//
//			byte[] input = clearText.getBytes();
//
//			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
//			byte[] encrypted = cipher.doFinal(input);
//
//			return new String(encrypted);
//		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
//				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	public static String encrypt(final String clearText) {
////		System.out.println("encrypt");
//		try {
//			byte[] messageBytes = clearText.getBytes(StandardCharsets.UTF_8);
//			KeyGenerator gen = KeyGenerator.getInstance("AES");
//			gen.init(192);
//			/* 128-bit AES */
//			SecretKey secret = gen.generateKey();
//			byte[] key = secret.getEncoded();
////    printByteArray("key", key);
//
//			SecureRandom secRnd = SecureRandom.getInstance("SHA1PRNG");
//			byte[] ivBytes = new byte[16];
//			// Generate a new IV.
//			secRnd.nextBytes(ivBytes);
////    printByteArray("iv", ivBytes);
//
//			byte[] cypher = transform(Cipher.ENCRYPT_MODE, key, ivBytes, messageBytes);
////    printByteArray("cypher", cypher);
//
////			byte[] xorkey = xorByteArray(keyByte, key);
////    printByteArray("xorkey", xorkey);
//
////			String xorhex = toHexString(xorkey);
//			String xorhex = toHexString(key);
////    System.out.println("xorhex: " + xorhex);
//			String ivhex = toHexString(ivBytes);
////    System.out.println("ivhex: " + ivhex);
//			String cyphex = toHexString(cypher);
////    System.out.println("cyphex: " + cyphex);
//
//			return xorhex + ivhex + cyphex;
//		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException ex) {
//			LOG.warn("encrypt exception", ex);
//		}
//		return null;
//	}
//
//	public static String decrypt(final String cypher) {
////		System.out.println("decrypt");
//
//		String xorhex = cypher.substring(0, 48);
////    System.out.println("xorhex: " + xorhex);
//		String ivhex = cypher.substring(48, 80);
////    System.out.println("ivhex: " + ivhex);
//		String cyphex = cypher.substring(80);
////    System.out.println("cyphex: " + cyphex);
//
//		byte[] xorbyte = toByteArray(xorhex);
////		byte[] key = xorByteArray(xorbyte, keyByte);
////    printByteArray("xor", xorbyte);
////    printByteArray("key", key);
//
//		byte[] ivBytes = toByteArray(ivhex);
////    printByteArray("iv", ivBytes);
//		byte[] cyphbyte = toByteArray(cyphex);
////    printByteArray("cypher", cyphbyte);
//
//		try {
////			return new String(transform(Cipher.DECRYPT_MODE, key, ivBytes, cyphbyte));
//			return new String(transform(Cipher.DECRYPT_MODE, xorbyte, ivBytes, cyphbyte));
//		} catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException ex) {
//			LOG.warn("decrypt exception", ex);
//		}
//		return null;
//	}

	private static String toHexString(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}

	private static byte[] toByteArray(String hexstring) {
		return DatatypeConverter.parseHexBinary(hexstring);
	}

//	private static byte[] xorByteArray(byte[] arr1, byte[] arr2) {
//		byte[] xorbyte = new byte[24];
//		int i = 0;
//		for (byte b : arr1) {
//			xorbyte[i] = (byte) (b ^ arr2[i++]);
//		}
//		return xorbyte;
//	}

//  private static void printByteArray(String prefix, byte[] arr) {
//    System.out.print(prefix + ":");
//    for (byte b : arr) {
//      System.out.print(b + " ");
//    }
//    System.out.println();
//  }

//	private static byte[] transform(final int mode, final byte[] keyBytes, final byte[] ivBytes,
//			final byte[] messageBytes) throws InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
//		final SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
//		final IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
//		byte[] transformedBytes = null;
//
//			// final Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
//			final Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
//
//			cipher.init(mode, keySpec, ivSpec);
//
//			transformedBytes = cipher.doFinal(messageBytes);
//
//		return transformedBytes;
//	}

}
