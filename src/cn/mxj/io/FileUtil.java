/**
 * 
 */
package cn.mxj.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import cn.mxj.string.StringUtil;

/**
 * 文件系统工具类，提供对文件的拷贝、转移、删除操作和对文件夹的创建、删除操作
 * 
 * @author fl
 * 
 */
public class FileUtil {

	/**
	 * 拷贝文件到新地方
	 * 
	 * @param sourFileName
	 *            源文件路径，如：F:/TestFolder/f4/1.txt
	 * @param destFileName
	 *            目标文件路径，如 F:/TestFolder/f5/2.txt。 自动创建目标路径中的文件夹和文件。
	 * @return
	 */
	public static boolean copyFile(String sourFileName, String destFileName) {
		int bytesum = 0;
		int byteread = 0;
		if(!new File(sourFileName).exists()){
			System.out.println(sourFileName + " not exists; java-lib cn.skyclass.io.FileUtil.java");
			return false;
		}
		try {
			createFolders(destFileName, true);

			InputStream in = new FileInputStream(sourFileName);
			FileOutputStream out = new FileOutputStream(destFileName);
			byte[] buffer = new byte[1024];
			while ((byteread = in.read(buffer)) != -1) {
				bytesum += byteread;
				out.write(buffer, 0, byteread);
			}
			in.close();
			return true;
		} catch (Exception ex) {
			AppLogger.getInstance().exception(ex);
			return false;
		}
	}

	/**
	 * 转移文件，删除原文件
	 * 
	 * @param sourFileName
	 *            eg. F:/TestFolder/f4/1.txt
	 * @param destFileName
	 *            eg. F:/TestFolder/f3/2.txt
	 * @return
	 */
	public static boolean moveFile(String sourFileName, String destFileName) {
		boolean b = copyFile(sourFileName, destFileName);
		if (b) {
			File f = new File(sourFileName);
			f.delete();
		}
		return b;
	}

	/**
	 * 删除一个文件
	 * 
	 * @param fileName
	 *            eg. F:/TestFolder/f4/1.txt
	 */
	public static void deleteFile(String fileName) {
		File f = new File(fileName);
		f.delete();
	}

	/**
	 * 创建 path 所包含的所有文件夹
	 * 
	 * @param path
	 * @return 返回是否有文件夹被创建
	 */
	public static boolean createFolders(String path) {
		return createFolders(path, false);
	}

	/**
	 * 创建 path 所包含的所有文件夹
	 * 
	 * @param path
	 *            路径字符串，如：F:\\TestFolder\\f4\\ , F:/TestFolder/f4/1.txt
	 * @param excludeFileName
	 *            是否排除路径中包含的文件名称（依靠最后的点号来判断文件名）。如果为 true，则路径
	 *            F:/TestFolder/f4/1.txt 将会创建 TestFolder 和 f4 两个文件夹（排除
	 *            1.txt），否则将会创建包括 1.txt 在内的三个文件夹；此参数对诸如 F:/TestFolder/f5/
	 *            等不含文件名称的路径不会产生影响。
	 * @return 返回是否有文件夹被创建
	 */
	public static boolean createFolders(String path, boolean excludeFileName) {
		if (StringUtil.isNullOrEmpty(path)) {
			return false;
		}

		if (excludeFileName) {
			int dot = path.lastIndexOf(".");
			int slash = path.lastIndexOf("/");
			int slashRev = path.lastIndexOf("\\");

			// 最后的一个点号必须在斜杠之后，才能被视为是文件名的扩展名部分
			if (dot > slash && dot > slashRev) {
				path = path.substring(0, Math.max(slash, slashRev));
			}
		}

		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除 path 对应的文件夹
	 * 
	 * @param path
	 *            eg. F:/TestFolder/folder1
	 */
	public static void deleteFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		} else if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				deleteFolder(f.getPath());
			}
			file.delete();
		}
	}

	/**
	 * 获取父目录的路径
	 * 
	 * @param path
	 * @return
	 */
	public static String parentFolderPath(String path) {
		if (path.charAt(path.length() - 1) == '\\') {
			path = path.substring(0, path.length() - 2);
		}

		String parentPath = path.substring(0, path.lastIndexOf('\\'));
		return parentPath;
	}

	/**
	 * 判断指定的路径是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean exist(String path) {
		File file = new File(path);
		return file.exists();
	}

}
