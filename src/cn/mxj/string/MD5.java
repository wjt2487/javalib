/**
 * 
 */
package cn.mxj.string;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.mxj.io.AppLogger;

/**
 * md5 加密算法的实现
 * 
 * @author fl
 * 
 */
public class MD5 {

	/**
	 * 对输入的字符串进行 md5 加密后输出；若 input 为 null 或是空字符串，则输出一个空字符串
	 * 
	 * @param input
	 * @return
	 */
	public static String encrypt(String input) {
		if (StringUtil.isNullOrEmpty(input)) {
			return "";
		}

		String result = input;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(input.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException ex) {
			result = input;
			AppLogger.getInstance().exception(ex);
		}
		return result;
	}

	public static String encryptEx(String input) {
		String encrypted = encrypt(input);
		return encrypt(encrypted + encrypted);
	}

}
