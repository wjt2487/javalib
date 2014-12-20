/*
 * @(#)RSAPrivateKey.java
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
import java.security.PrivateKey;

/**
 * A class that encapsulates a private key for use in RSA decryption.
 * 
 * @author Jared Klett
 * @version 1.2.1 (7/13/00)
 */

public class RSAPrivateKey implements PrivateKey {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2780557620439198210L;

	private BigInteger s;

	private BigInteger modulo;

	private String algorithm;

	/**
	 * Creates a new private key with the passed value and modulo.
	 * 
	 * @param privateKey
	 *            The private key.
	 * @param modulo
	 *            The modulo.
	 */
	public RSAPrivateKey(BigInteger privateKey, BigInteger modulo,
			String algorithm) {
		this.s = privateKey;
		this.modulo = modulo;
	}

	/**
	 * Retrieves the private key.
	 * 
	 * @return The private key as a <CODE>java.math.BigInteger</CODE>.
	 */
	public BigInteger getPrivateKey() {
		return s;
	}

	/**
	 * Retrieves the modulo value associated with this private key.
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
	 * Returns the private key as a hex string.
	 * 
	 * @return The hexadecimal representation of this private key.
	 */
	public String toString() {
		return toString(16);
	}

	/**
	 * Returns the private key as a string according to the specified radix.
	 * 
	 * @param radix
	 *            The radix to be used.
	 * @return The string representation of this private key.
	 */
	public String toString(int radix) {
		return s.toString(radix);
	}

}
