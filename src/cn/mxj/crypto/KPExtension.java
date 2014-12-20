package cn.mxj.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

public class KPExtension {

	private KeyPair pair = null;

	private PublicKey pub = null;

	private PrivateKey priv = null;

	private Signature sig = null;

	public KPExtension() {
		try {
			sig = Signature.getInstance("SHA1withDSA");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
			SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
			keyGen.initialize(1024, rand);
			pair = keyGen.generateKeyPair();
			pub = pair.getPublic();
			priv = pair.getPrivate();
			return new KeyPair(pub, priv);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public PublicKey retrievePublicFromFile(String filename) {
		try {
			sig = Signature.getInstance("SHA1withDSA");

			FileInputStream fis = new FileInputStream(filename);
			byte[] encKey = new byte[fis.available()];
			fis.read(encKey);
			fis.close();

			X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(encKey);
			KeyFactory kf = KeyFactory.getInstance("DSA");

			pub = kf.generatePublic(pkSpec);
			return pub;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public PublicKey retrievePublicFromBytes(byte[] encKey) {
		try {
			sig = Signature.getInstance("SHA1withDSA");

			X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(encKey);
			KeyFactory kf = KeyFactory.getInstance("DSA");

			pub = kf.generatePublic(pkSpec);
			return pub;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public PrivateKey retrievePrivateFromFile(String filename) {
		try {
			sig = Signature.getInstance("SHA1withDSA");

			FileInputStream fis = new FileInputStream(filename);
			byte[] encKey = new byte[fis.available()];
			fis.read(encKey);
			fis.close();

			X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(encKey);
			KeyFactory kf = KeyFactory.getInstance("DSA");

			priv = kf.generatePrivate(pkSpec);
			return priv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public PrivateKey retrievePrivateFromBytes(byte[] encKey) {
		try {
			sig = Signature.getInstance("SHA1withDSA");

			X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(encKey);
			KeyFactory kf = KeyFactory.getInstance("DSA");

			priv = kf.generatePrivate(pkSpec);
			return priv;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void writePubToFile(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] key = pub.getEncoded();
			fos.write(key);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writePrivToFile(String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			byte[] key = priv.getEncoded();
			fos.write(key);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] getPub() {
		return pub.getEncoded();
	}

	public byte[] signStringKP(String data) {
		try {
			sig.initSign(priv);
			sig.update(data.getBytes());
			byte[] sigg = sig.sign();
			return sigg;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: Verification failed!");
			return null;
		}
	}

	public boolean verifyStringKP(String data, byte[] sigBytes) {
		try {
			sig.initVerify(pub);
			sig.update(data.getBytes());
			return sig.verify(sigBytes);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: Verification failed!");
			return false;
		}
	}

	public byte[] encryptStringKP(String data, PrivateKey key) {
		byte[] ciphertext = new byte[data.length()];

		return ciphertext;
	}

	public String decryptStringKP(byte[] data, PublicKey key) {
		String str = new String();

		return str;
	}

}