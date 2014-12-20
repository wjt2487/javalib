/*
 * @(#)RSACrypto.java
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
import java.util.StringTokenizer;

/**
 * A class to encrypt and decrypt plaintext and ciphertext, respectively.
 * 
 * @author Jared Klett
 * @version 1.2.1 (7/13/00)
 */

public class RSACrypto {

	/**
	 * Encrypts the plaintext.
	 * 
	 * @param message
	 *            The plaintext message to be encrypted.
	 * @param publickey
	 *            The public key.
	 * @param modulo
	 *            The modulo value.
	 * 
	 * @return The ciphertext as a <code>BigInteger</code> array.
	 */
	public static BigInteger[] encrypt(String message, RSAPublicKey publicKey)
			throws ArithmeticException {
		byte[] temp;
		byte[] digits = message.getBytes();

		BigInteger[] bigdigits = new BigInteger[digits.length];

		for (int i = 0; i < bigdigits.length; i++) {
			temp = new byte[1];
			temp[0] = digits[i];
			bigdigits[i] = new BigInteger(temp);
		}

		BigInteger[] encrypted = new BigInteger[bigdigits.length];

		for (int j = 0; j < bigdigits.length; j++)
			encrypted[j] = bigdigits[j].modPow(publicKey.getPublicKey(),
					publicKey.getModulo());

		return encrypted;
	}
	
	/**
	 * 用私钥加密
	 * @param message
	 * @param privateKey
	 * @return
	 * @throws ArithmeticException
	 */
	public static BigInteger[] encrypt(String message, RSAPrivateKey privateKey)
			throws ArithmeticException {
		byte[] temp;
		byte[] digits = message.getBytes();

		BigInteger[] bigdigits = new BigInteger[digits.length];

		for (int i = 0; i < bigdigits.length; i++) {
			temp = new byte[1];
			temp[0] = digits[i];
			bigdigits[i] = new BigInteger(temp);
		}

		BigInteger[] encrypted = new BigInteger[bigdigits.length];

		for (int j = 0; j < bigdigits.length; j++)
			encrypted[j] = bigdigits[j].modPow(privateKey.getPrivateKey(),
					privateKey.getModulo());

		return encrypted;
	}

	public static BigInteger[] encrypt(BigInteger[] bigdigits,
			RSAPublicKey publicKey) throws ArithmeticException {

		BigInteger[] encrypted = new BigInteger[bigdigits.length];

		for (int j = 0; j < bigdigits.length; j++)
			encrypted[j] = bigdigits[j].modPow(publicKey.getPublicKey(),
					publicKey.getModulo());
		return encrypted;
	}

	public static BigInteger[] sign(String message, RSAPrivateKey privateKey)
			throws ArithmeticException {
		byte[] temp;
		byte[] digits = message.getBytes();

		BigInteger[] bigdigits = new BigInteger[digits.length];

		for (int i = 0; i < bigdigits.length; i++) {
			temp = new byte[1];
			temp[0] = digits[i];
			bigdigits[i] = new BigInteger(temp);
		}

		BigInteger[] encrypted = new BigInteger[bigdigits.length];

		for (int j = 0; j < bigdigits.length; j++)
			encrypted[j] = bigdigits[j].modPow(privateKey.getPrivateKey(),
					privateKey.getModulo());

		return encrypted;
	}

	public static BigInteger[] sign(BigInteger[] bigdigits,
			RSAPrivateKey privateKey) throws ArithmeticException {
		BigInteger[] encrypted = new BigInteger[bigdigits.length];

		for (int j = 0; j < bigdigits.length; j++)
			encrypted[j] = bigdigits[j].modPow(privateKey.getPrivateKey(),
					privateKey.getModulo());

		return encrypted;
	}

	// **********************************************************************

	/**
	 * Decrypts the ciphertext.
	 * 
	 * @param encrypted
	 *            The ciphertext as a <code>BigInteger</code> array.
	 * @param privatekey
	 *            The private key.
	 * @param modulo
	 *            The modulo value.
	 * 
	 * @return The decrypted plaintext.
	 */
	public static String decrypt(BigInteger[] encrypted,
			RSAPrivateKey privateKey) throws ArithmeticException {
		BigInteger[] decrypted = new BigInteger[encrypted.length];

		for (int i = 0; i < decrypted.length; i++)
			decrypted[i] = new BigInteger(encrypted[i].toString()).modPow(
					privateKey.getPrivateKey(), privateKey.getModulo());

		char[] array = new char[decrypted.length];

		for (int j = 0; j < array.length; j++)
			array[j] = (char) (decrypted[j].intValue());

		return new String(array);
	}
	
	public static String decrypt(BigInteger[] encrypted,
			RSAPublicKey publicKey) throws ArithmeticException {
		BigInteger[] decrypted = new BigInteger[encrypted.length];

		for (int i = 0; i < decrypted.length; i++)
			decrypted[i] = new BigInteger(encrypted[i].toString()).modPow(
					publicKey.getPublicKey(),publicKey.getModulo());

		char[] array = new char[decrypted.length];

		for (int j = 0; j < array.length; j++)
			array[j] = (char) (decrypted[j].intValue());

		return new String(array);
	}

	public static BigInteger[] decryptToBigInt(BigInteger[] encrypted,
			RSAPrivateKey privateKey) throws ArithmeticException {
		BigInteger[] decrypted = new BigInteger[encrypted.length];

		for (int i = 0; i < decrypted.length; i++)
			decrypted[i] = new BigInteger(encrypted[i].toString()).modPow(
					privateKey.getPrivateKey(), privateKey.getModulo());

		return decrypted;
	}

	public static String verify(BigInteger[] encrypted, RSAPublicKey publicKey)
			throws ArithmeticException {
		BigInteger[] decrypted = new BigInteger[encrypted.length];

		for (int i = 0; i < decrypted.length; i++)
			decrypted[i] = new BigInteger(encrypted[i].toString()).modPow(
					publicKey.getPublicKey(), publicKey.getModulo());

		char[] array = new char[decrypted.length];

		for (int j = 0; j < array.length; j++)
			array[j] = (char) (decrypted[j].intValue());

		return new String(array);
	}

	public static BigInteger[] verifyToBigInt(BigInteger[] encrypted,
			RSAPublicKey publicKey) throws ArithmeticException {
		BigInteger[] decrypted = new BigInteger[encrypted.length];

		for (int i = 0; i < decrypted.length; i++)
			decrypted[i] = new BigInteger(encrypted[i].toString()).modPow(
					publicKey.getPublicKey(), publicKey.getModulo());

		return decrypted;
	}

	// *****************************************************************

	public static byte[] bigIntToByteArr(BigInteger[] biArray) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < biArray.length; i++)
			sb.append(biArray[i].toString() + ":");

		return sb.toString().getBytes();
	}

	public static BigInteger[] byteArrToBigInt(byte[] byteArray) {
		String biArrString = new String(byteArray);
		StringTokenizer st = new StringTokenizer(biArrString, ":");

		BigInteger[] biArray = new BigInteger[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens())
			biArray[i++] = new BigInteger(st.nextToken());

		return biArray;
	}

	public static String bigIntToString(BigInteger[] biArray) {
		char[] array = new char[biArray.length];

		for (int i = 0; i < array.length; i++)
			array[i] = (char) biArray[i].intValue();

		return new String(array);
	}

	public static BigInteger[] stringToBigInt(String str) {
		byte[] temp;
		byte[] digits = str.getBytes();
		BigInteger[] bigdigits = new BigInteger[digits.length];

		for (int i = 0; i < bigdigits.length; i++) {
			temp = new byte[1];
			temp[0] = digits[i];
			bigdigits[i] = new BigInteger(temp);
		}

		return bigdigits;
	}

}
