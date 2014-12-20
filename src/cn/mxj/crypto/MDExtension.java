package cn.mxj.crypto;

import java.security.MessageDigest;

public class MDExtension {

	private MessageDigest md;

	public MDExtension(String instance) {
		try {
			md = MessageDigest.getInstance(instance);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] digestString(String dataString) {
		try {
			md.reset();
			byte[] dataBytes = dataString.getBytes();
			md.update(dataBytes);
			return md.digest();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}