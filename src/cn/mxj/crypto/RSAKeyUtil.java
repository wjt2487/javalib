/**
 * 
 */
package cn.mxj.crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author fl
 * 
 */
public class RSAKeyUtil {

	public static void writeKeys(String pubFilename, String privFilename,
			KeyPair kp) {
		writePubToFile(pubFilename, kp.getPublic());
		writePrivToFile(privFilename, kp.getPrivate());
	}

	public static void writePubToFile(String filename, PublicKey pub) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(fos)), true);
			out.println(pub);
			if (pub instanceof RSAPublicKey) {
				out.println(((RSAPublicKey) pub).getPublicKey());
				out.println(((RSAPublicKey) pub).getModulo());
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writePrivToFile(String filename, PrivateKey priv) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(fos)), true);
			out.println(priv);
			if (priv instanceof RSAPrivateKey) {
				out.println(((RSAPrivateKey) priv).getPrivateKey());
				out.println(((RSAPrivateKey) priv).getModulo());
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static KeyPair readKeys(String pubFilename, String privFilename) {
		PublicKey pk = readPubFromFile(pubFilename);
		PrivateKey sk = readPrivFromFile(privFilename);

		return new KeyPair(pk, sk);
	}

	public static PublicKey readPubFromFile(String filename) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));

			// String pk = in.readLine();
			in.readLine();

			BigInteger pub = new BigInteger(in.readLine());
			BigInteger mod = new BigInteger(in.readLine());
			return new RSAPublicKey(pub, mod, "RSA");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static PrivateKey readPrivFromFile(String filename) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));

			// String pk = in.readLine();
			in.readLine();

			BigInteger priv = new BigInteger(in.readLine());
			BigInteger mod = new BigInteger(in.readLine());
			return new RSAPrivateKey(priv, mod, "RSA");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
