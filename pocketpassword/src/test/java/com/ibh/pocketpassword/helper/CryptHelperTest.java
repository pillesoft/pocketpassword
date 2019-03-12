package com.ibh.pocketpassword.helper;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Hex;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibh.pocketpassword.helper.CryptHelper;

public class CryptHelperTest {

	private static String cryptpwd; 
	@BeforeClass
	public static void setUpClass() throws Exception {
		cryptpwd = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
		CryptHelper.init(cryptpwd);
	}
	
	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void testEncrypt() {
//				
//		String res0 = CryptHelper.encrypt("h");
//		System.out.println(res0);
//		System.out.println(res0.length());
//
//		String res1 = CryptHelper.encrypt("hello");
//		System.out.println(res1);
//		System.out.println(res1.length());
//		String res2 = CryptHelper.encrypt("hello na mi van? nincs is semmi?");
//		System.out.println(res2);
//		System.out.println(res2.length());
//
//		String res3 = CryptHelper.encrypt("hello na mi van? nincs is semmi? És veled?");
//		System.out.println(res3);
//		System.out.println(res3.length());
//
//		assertNotNull("res1 is null", res1);
//		assertNotNull("res2 is null", res2);
//		
//		
//	}

	@Test
	public void testEncryptOneCharChange() {
				
		String res1 = CryptHelper.encrypt("hello");
		String res2 = CryptHelper.encrypt("he1lo");
		
		assertNotSame("res1 is same as res2", res1, res2);
		
	}

	@Test
	public void testEncryptDecrypt() {
				
		String res1 = CryptHelper.encrypt("hello");
		String res2 = CryptHelper.decrypt(res1);
		
		assertEquals("encrypt-decrypt is nok", res2, "hello");
		
	}

	@Test
	public void testEncrypt2x() {
				
		String res1 = CryptHelper.encrypt("hello");
		String res2 = CryptHelper.encrypt("hello");
		
		assertNotSame("two encrypts are nok", res1, res2);
		
	}
	
//	@Test
//	public void testDecryptChanged() {
//				
//		String res1 = CryptHelper.encrypt("na mi van wagner úr?");
//		char ch = res1.charAt(2);
////		System.out.println(ch);
//		
//		int ascii = (int)ch;
//		ascii++;
//		char ch2 = (char)ascii;
//		if (ch == 'F') {
//			ch2 = 'A';
//		}
////		System.out.println(ch2);
//		
//		String res2 = CryptHelper.decrypt(res1.replace(ch, ch2));
//		
//		assertNull("decrypt is nok", res2);
//	}

//	@Test
//	public void testDecrypt() {
//		String decrypt = "2190C2A95080BC189AACF9CC1A2818500AA81286FD3DEA57" +
//		"ABE17F8863D2CD74E6F37D97F468694D" +
//		"698D086BEC8CB39A984E8F79FF8864F4BBBE34C785B400576659AA7BED4D3737";
//				
//		String res1 = CryptHelper.decrypt(decrypt);
//		assertEquals("decrypt is NOK", "na mi van wagner úr?", res1);
//		
//	}
	
//	@Test
//	public void testDemo() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
//		String password = "password";
//
//        int keyLength = 256;
//        int saltLength = keyLength / 8; // It's bytes, not bits.
//        int iterations = 65536;
//        byte[] salt = new SecureRandom().generateSeed(saltLength);
//        printByteArray("salt", salt);
//        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
//
//        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
//        Key passwordKey = secretKeyFactory.generateSecret(keySpec);
//        SecretKeySpec skey = new SecretKeySpec(passwordKey.getEncoded(), "AES");
//
//        byte[] iv = new byte[16]; 
//        new SecureRandom().nextBytes(iv);
//        printByteArray("iv", iv);
//        final IvParameterSpec ivSpec = new IvParameterSpec(iv);
//        
//        byte[] wallet = "1234567890123456789012345678901".getBytes();
//        printText("Decrypted wallet: ", wallet);
//
//        Cipher encryptingCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        encryptingCipher.init(Cipher.ENCRYPT_MODE, skey, ivSpec);
//        byte[] cipherText = encryptingCipher.doFinal(wallet);
//        printByteArray("Encrypted wallet", cipherText);
//
//        Cipher decryptingCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//        byte[] iv2 = encryptingCipher.getIV();
//        printByteArray("iv2", iv2);
//        decryptingCipher.init(Cipher.DECRYPT_MODE, skey, new IvParameterSpec(iv2));
//        byte[] plainText = decryptingCipher.doFinal(cipherText);
//        printText("Decrypted wallet: ", plainText);
//
//	}

	private static void printText(String name, byte[] bytes) {
        System.out.println(name + ": "+ new String(bytes));
        System.out.println(name + "length: " + bytes.length + " bytes, " + bytes.length * 8 + " bits.");
        System.out.println("\r\n");
	}
	
	 private static void printByteArray(String name, byte[] bytes) {
	        System.out.println(name + ": "+ Hex.encodeHexString(bytes));
	        System.out.println(name + " length: " + bytes.length + " bytes, " + bytes.length * 8 + " bits.");
	        System.out.println("\r\n");
	}
}
