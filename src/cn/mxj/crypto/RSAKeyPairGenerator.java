/*
 * @(#)RSAKeyPairGenerator.java
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
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * A class that will generate pairs of public and private keys based on the RSA
 * algorithm.
 * 
 * @author Jared Klett
 * @version 1.2.1 (7/13/00)
 */

public class RSAKeyPairGenerator extends KeyPairGenerator {

	// A BigInteger representation of the number one.
	//
	private static final BigInteger ONE = new BigInteger("1");

	// The modulo value.
	//
	private BigInteger N;

	// Random prime number x.
	//
	private BigInteger x;

	// Random prime number y.
	//
	private BigInteger y;

	// The bit precision the keys will be generated by.
	//
	private int strength;

	// The random number generator for this key pair
	//
	private SecureRandom random;

	/**
	 * Creates a new key pair generator, with a default strength of 128 bits.
	 */
	public RSAKeyPairGenerator() {
		this(128);
	}

	/**
	 * Creates a new Keys instance, with the passed value precision. Also
	 * generates the random prime x and y values, which are used to calculate
	 * the modulo value.
	 * 
	 * @param strength
	 *            The strength of keys to be generated.
	 */
	public RSAKeyPairGenerator(int strength) {
		this(strength, null, null);
	}

	/**
	 * Creates a new Keys instance, with the passed values precision, x and y.
	 * Based on the passed x and y values, it calculates the modulo value.
	 * 
	 * @param strength
	 *            The strength of keys to be generated.
	 * @param x
	 *            blah
	 * @param y
	 *            blah
	 */
	public RSAKeyPairGenerator(int strength, BigInteger x, BigInteger y) {
		super("RSA");

		initialize(strength);

		// If x or y is null, generate new values
		//
		if (x == null || y == null) {
			x = new BigInteger(strength, 99, random);
			y = new BigInteger(strength, 99, random);

			while (!x.isProbablePrime(100)) {
				x = new BigInteger(strength, 99, random);
			}

			while (!y.isProbablePrime(100)) {
				y = new BigInteger(strength, 99, random);
			}
		}

		this.x = x;
		this.y = y;

		N = x.multiply(y);
	}

	/**
	 * Initializes this generator with the passed key strength. This will create
	 * a <CODE>java.security.SecureRandom</CODE> object to be used in key
	 * generation.
	 * 
	 * @param strength
	 *            The strength of keys to be generated.
	 */
	public void initialize(int strength) {
		initialize(strength, null);
	}

	/**
	 * Initializes this generator with the passed key strength and pseudo-random
	 * number generator. It is recommended that an instance of a
	 * <CODE>java.security.SecureRandom</CODE> object be passed, although a
	 * <CODE>java.util.Random</CODE> object can be used.
	 * 
	 * @param strength
	 *            The strength of keys to be generated.
	 * @param random
	 *            The random number generator to be used.
	 */
	public void initialize(int strength, SecureRandom random) {
		setStrength(strength);

		if (random == null) {
			random = new SecureRandom();
		}

		this.random = random;
	}

	/**
	 * Generates an RSA public and private key. Returns a
	 * <CODE>java.security.KeyPair</CODE> object containing those keys.
	 * 
	 * @return The public and private keys as a
	 *         <CODE>java.security.KeyPair</CODE>.
	 */
	public KeyPair generateKeyPair() {

		// Generate the private key
		//
		BigInteger s = new BigInteger(strength, 99, random);

		while (!s.isProbablePrime(100)) {
			s = new BigInteger(strength, 99, random);
		}

		// Calculate the public key
		//
		BigInteger p = s.modInverse(x.subtract(ONE).multiply(y.subtract(ONE)));

		return new KeyPair(new RSAPublicKey(p, N, getAlgorithm()),
				new RSAPrivateKey(s, N, getAlgorithm()));
	}

	/**
	 * Retrieves the strength of the keys this generator will create.
	 * 
	 * @return The key strength, in bits.
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Sets the strength of the keys this generator will create.
	 * 
	 * @param strength
	 *            The new key strength, in bits.
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

}
