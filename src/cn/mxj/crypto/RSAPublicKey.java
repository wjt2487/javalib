/*
 * @(#)RSAPublicKey.java
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
import java.security.PublicKey;

/**
 * A class that encapsulates a private key for use in RSA decryption.
 * 
 * @author Jared Klett
 * @version 1.2.1 (7/13/00)
 */

public class RSAPublicKey implements PublicKey {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2258911239973625158L;

	private BigInteger p;

	private BigInteger modulo;

	private String algorithm;

	/**
	 * Creates a new public key with the passed value and modulo.
	 * 
	 * @param publicKey
	 *            The public key.
	 * @param modulo
	 *            The modulo.
	 */
	public RSAPublicKey(BigInteger publicKey, BigInteger modulo,
			String algorithm) {
		this.p = publicKey;
		this.modulo = modulo;
	}

	/**
	 * Retrieves the public key.
	 * 
	 * @return The public key as a <CODE>java.math.BigInteger</CODE>.
	 */
	public BigInteger getPublicKey() {
		return p;
	}

	/**
	 * Retrieves the modulo value associated with this public key.
	 * 
	 * @return The modulo value as a <CODE>java.math.BigInteger</CODE>.
	 */
	public BigInteger getModulo() {
		return modulo;
	}

	/**
	 * Retrieves the string identifier of the algorithm this key belongs to.
	 * 
	 * @return The name of the algorithm.
	 */
	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Returns null.
	 * 
	 * @return null.
	 */
	public String getFormat() {
		return null;
	}

	/**
	 * Returns null.
	 * 
	 * @return null.
	 */
	public byte[] getEncoded() {
		return null;
	}

	/**
	 * Returns the public key as a hex string.
	 * 
	 * @return The hexadecimal representation of this public key.
	 */
	public String toString() {
		return toString(16);
	}

	/**
	 * Returns the public key as a string according to the specified radix.
	 * 
	 * @param radix
	 *            The radix to be used.
	 * @return The string representation of this public key.
	 */
	public String toString(int radix) {
		return p.toString(radix);
	}

}
