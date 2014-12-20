package cn.mxj.skoserver;

import cn.mxj.string.DES;

public class Verify {

	private final String ENCRYPT_KEY = "G8H%^1*(HR%#KT()^$E3G";

	private DES des;

	public Verify() {
		try {
			des = new DES(ENCRYPT_KEY);
		} catch (Exception e) {
		}
	}

	public int getSessionId() {
		return (int) (Math.random() * 1000000);
	}

	public String getToken(int sessionid) {
		String sToken = "";

		try {
			sToken = des.encrypt(new Integer(sessionid).toString());
		} catch (Exception e) {
		}

		return sToken;
	}

	public boolean checkSession(int sessionId, String token) {
		boolean result = false;

		try {
			result = (des.encrypt(new Integer(sessionId).toString())
					.equalsIgnoreCase(token));
		} catch (Exception e) {
		}

		return result;
	}
}
