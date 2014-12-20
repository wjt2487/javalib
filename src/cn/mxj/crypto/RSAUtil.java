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
import java.util.ArrayList;
import java.util.List;

/**
 * @author fl
 * 
 */
public class RSAUtil {

	public static void writeEncryptDataToFile(String filename, BigInteger[] data) {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(fos)), true);
			for (int i = 0; i < data.length; i++) {
				out.println(data[i]);
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static BigInteger[] readEncryptDataFromFile(String filename) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename)));
			List<BigInteger> dataList = new ArrayList<BigInteger>();
			String line = in.readLine();
			while (line != null) {
				dataList.add(new BigInteger(line));
				line = in.readLine();
			}
			return dataList.toArray(new BigInteger[] {});
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
