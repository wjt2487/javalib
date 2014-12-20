/*
 * @(#)CryptoTest.java
 *
 * This software is released under the GNU General Public License.
 * http://www.gnu.org/copyleft/gpl.html
 *
 * Under no circumstances does the author of this software assume
 * any sort of liability pertaining to the use, modification, or
 * distribution of this software.
 *
 * In other words, use this code AT YOUR OWN RISK!
 */

package cn.mxj.crypto;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.SecureRandom;

/**
 * An application class to test and benchmark the functionality of the RSA
 * cryptography classes.
 * 
 * @author Jared Klett
 * @version 1.0 (7/14/00)
 */

public class RSATest {

	public static String getPlainText() {
		PrivateKey priv = RSAKeyUtil.readPrivFromFile("f:/easikey.priv");
		BigInteger[] ciphertext = RSAUtil
				.readEncryptDataFromFile("f:/ciphertext.txt");
		String plainText = RSACrypto.decrypt(ciphertext, (RSAPrivateKey) priv);
		return plainText;
	}

	public static void runTest(String plainText) {
		int strength = 128;

		// Generate to random prime numbers for key generation
		SecureRandom random = new SecureRandom();
		BigInteger x = new BigInteger(strength, 100, random);
		BigInteger y = new BigInteger(strength, 100, random);

		// Begin benchmark
		long start = System.currentTimeMillis();

		// Generate key pair
		RSAKeyPairGenerator kpg = new RSAKeyPairGenerator(strength, x, y);
		KeyPair pair = kpg.generateKeyPair();

		// End benchmark
		long delta = System.currentTimeMillis() - start;

		System.out.println("Keys generated in " + delta + " ms");
		System.out.println("Public key: " + pair.getPublic());
		System.out.println("Private key: " + pair.getPrivate());
		System.out.println();

		// Test write to and read from file
		RSAKeyUtil.writeKeys("f:/easikey.pub", "f:/easikey.priv", pair);
		pair = RSAKeyUtil.readKeys("f:/easikey.pub", "f:/easikey.priv");

		System.out.println("After io accessing:");
		System.out.println("Public key: " + pair.getPublic());
		System.out.println("Private key: " + pair.getPrivate());
		System.out.println();

		// Encrypt
		start = System.currentTimeMillis();
		BigInteger[] ciphertext = RSACrypto.encrypt(plainText,
				(RSAPublicKey) pair.getPublic());
		delta = System.currentTimeMillis() - start;

		System.out.println("Plain text: ");
		System.out.println(plainText);
		System.out.println();

		System.out.println("Ciphertext: ");
		for (int i = 0; i < ciphertext.length; i++)
			System.out.println(ciphertext[i]);
		System.out.println();

		System.out.println("Encrypted in " + delta + " ms");
		System.out.println();

		// Decrypt
		start = System.currentTimeMillis();
		String s = RSACrypto.decrypt(ciphertext, (RSAPrivateKey) pair
				.getPrivate());
		delta = System.currentTimeMillis() - start;

		// test write and read ciphertext through file
		RSAUtil.writeEncryptDataToFile("f:/ciphertext.txt", ciphertext);
		BigInteger[] ciphertextIO = RSAUtil
				.readEncryptDataFromFile("f:/ciphertext.txt");
		String s1 = RSACrypto.decrypt(ciphertextIO, (RSAPrivateKey) pair
				.getPrivate());

		System.out.println("Plaintext after decrypt: ");
		System.out.println(s);
		System.out.println();
		System.out.println(s1);
		System.out.println();

		System.out.println("Decrypted in " + delta + " ms");

		// Testing Big Integer Array Stuff

		// System.out.println("Testing Big Integer Conversion");
		// System.out.println("original: " + plainText);
		//
		// BigInteger[] biArray = RSACrypto.stringToBigInt(plainText);
		// String conv = RSACrypto.bigIntToString(biArray);
		// System.out.println("converted: " + conv);
		//
		// BigInteger[] biArray2 = new BigInteger[10];
		// for (int i = 0; i < 10; i++)
		// biArray2[i] = new BigInteger(128, random);
		// String conv2 = RSACrypto.bigIntToString(biArray2);
		// System.out.println("converted: " + conv2);
		//
		// BigInteger[] biArray3 =
		// RSACrypto.byteArrToBigInt(RSACrypto.bigIntToByteArr(biArray2));
		//
		// if (biArray2.equals(biArray3))
		// System.err.println("SUCCESS");
		// else {
		// System.err.println("FAIL");
		// for (int i = 0; i < biArray2.length; i++)
		// System.err.println(biArray2[i] + "\t" + biArray3[i]);
		// }

		// BigInteger[] biArray2 = new BigInteger[10];
		// for (int i=0; i<10; i++)
		// biArray2[i] = new BigInteger(8, random);
		// String conv2 = RSACrypto.bigIntToString(biArray2);
		// System.out.println("converted: " + conv2);

		// BigInteger[] biArray3 =
		// RSACrypto.stringToBigInt(conv2);

		// if (biArray2.equals(biArray3))
		// System.err.println("SUCCESS");
		// else {
		// System.err.println("FAIL");
		// for (int i=0; i<biArray2.length; i++)
		// System.err.println(biArray2[i] + "\t" +
		// biArray3[i]);
		// }

	}

}
