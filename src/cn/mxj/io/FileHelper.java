package cn.mxj.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 提供对文件的读写操作
 * 
 * @author fl
 * 
 */
public class FileHelper {

	/**
	 * 获取文件的大小，如操作失败则返回 0
	 * 
	 * @param path
	 * @return
	 */
	public static long getFileSize(String path) {
		try {
			File file = new File(path);
			return file.length();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 读取一个文件的内容，若文件不存在，则返回没有内容 StringBuffer（而不是 null）
	 * 
	 * @param fileName
	 *            目标文件
	 * @return
	 */
	public static StringBuffer readFile(String fileName) {
		return readFile(fileName, "utf-8");
	}

	/**
	 * 读取一个文件的内容，若文件不存在，则返回没有内容 StringBuffer（而不是 null）
	 * 
	 * @param fileName
	 *            目标文件
	 * @param encoding
	 *            目标文件编码
	 * 
	 * @return
	 */
	public static StringBuffer readFile(String fileName, String encoding) {
		StringBuffer sbOut = new StringBuffer();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(new File(fileName)), encoding));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sbOut.append(tempString);
			}
			reader.close();
		} catch (IOException ex) {
			AppLogger.getInstance().exception(ex);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sbOut;
	}

	/**
	 * 将 content 写入文件 fileName 中，若文件夹或文件不存在，则自动创建
	 * 
	 * @param fileName
	 *            写入的文件名称，如：F:/TestFolder/f4/1.txt
	 * @param content
	 * @param append
	 * @return
	 */
	public static boolean writeFile(String fileName, String content,
			boolean append) {
		return FileHelper.writeFile(fileName,content,append,"utf-8");
	}
	
	/**
	 * 将 content 写入文件 fileName 中，若文件夹或文件不存在，则自动创建
	 * 
	 * @param fileName
	 *            写入的文件名称，如：F:/TestFolder/f4/1.txt
	 * @param content
	 * @param append
	 * @param encoding
	 * @return
	 */
	public static boolean writeFile(String fileName, String content,
			boolean append,String encoding) {
		try {
			FileUtil.createFolders(fileName, true);
			File file = new File(fileName);
			file.createNewFile();
			Writer writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, append), encoding));
			writer.write(content);
			writer.close();
			return true;
		} catch (IOException ex) {
			AppLogger.getInstance().exception(ex);
			return false;
		}
	}
}
