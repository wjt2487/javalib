package cn.mxj.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipFile;

public class ZipUtil {
	static final int BUFFER = 2048;

	public static boolean zip(String srcPath, String destPath) {
		boolean b = false;
		try {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(destPath);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					dest));
			byte data[] = new byte[BUFFER];
			File f = new File(srcPath);
			File files[] = f.listFiles();

			for (int i = 0; i < files.length; i++) {
				FileInputStream fi = new FileInputStream(files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(files[i].getName());
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public static boolean unzip(String srcPath, String destPath) {
		boolean b = false;
		try {
			ZipFile zipFile = new ZipFile(srcPath);
			Enumeration emu = zipFile.entries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				if (entry.isDirectory()) {
					new File(destPath + entry.getName()).mkdirs();
					continue;
				}
				BufferedInputStream bis = new BufferedInputStream(zipFile
						.getInputStream(entry));
				File file = new File(destPath + entry.getName());
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER);

				int count;
				byte data[] = new byte[BUFFER];
				while ((count = bis.read(data, 0, BUFFER)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			zipFile.close();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String unzipfile = "D:\\UploadFiles\\teacher-platform\\licence-info\\licence.zip"; // 解压缩的文件名
		String saveTo = "d:\\";
		boolean success = ZipUtil.unzip(unzipfile, saveTo);
		System.out.println(success ? "ok" : "err");
	}
}
