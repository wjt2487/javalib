package cn.mxj.io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Random;

import cn.mxj.beans.LogInfoBean;
import cn.mxj.exception.ExceptionLevel;
import cn.mxj.string.DateFormatter;

/**
 * 自定义的日志记录类，日志文件保存在网站的 WEB-INF/data/logs/ 下
 * 
 * @author fl
 * 
 */
public class AppLogger {

	/**
	 * 使用默认路径来放置日志文件的日志记录器 默认路径为:
	 * {TomcatInstallationDir}/webapps/java-web-tester/WEB-INF/data/logs/
	 */
	public AppLogger() {
		String defaultPath = SitePathInfo.getInstance().getWebDataPath()
				+ "logs/";
		this.logDirPath = defaultPath;
		FileUtil.createFolders(logDirPath);
	}

	/**
	 * 自定义放置日志文件的路径的日志记录器
	 * 
	 * @param logDirPath
	 *            保存日志文件的根目录路径
	 */
	public AppLogger(String logDirPath) {
		this.logDirPath = logDirPath;
		FileUtil.createFolders(logDirPath);
	}

	/**
	 * eg: {TomcatInstallationDir}/webapps/java-web-tester/WEB-INF/data/logs/
	 */
	private String logDirPath;

	private static AppLogger instance;

	public static AppLogger getInstance() {
		if (instance == null) {
			instance = new AppLogger();
		}
		return instance;
	}

	/**
	 * 记录客户端的日志信息
	 * 
	 * @param value
	 */
	public void clientLog(LogInfoBean value) {
		if (value.isErrorLog()) {
			this.clientLog(value.getErrId(), value.getErrName(), value
					.getErrMessage(), value.getErrStackTrace());
		} else if (value.isFaultLog()) {
			this.clientLog(value.getFaultCode(), value.getFaultString(), value
					.getFaultDetail());
		} else if (value.isInfoLog()) {
			this.clientLog(value.getInfo());
		}
	}

	/**
	 * 记录客户端的日志信息
	 * 
	 * @param messages
	 */
	public void clientLog(String... messages) {
		printMsgs(true, messages);
		writeToFile(true, null, messages);
	}

	/**
	 * 记录日志信息
	 * 
	 * @param messages
	 */
	public void info(String... messages) {
		printMsgs(false, messages);
		writeToFile(false, null, messages);
	}

	/**
	 * 记录异常信息
	 * 
	 * @param ex
	 */
	public void exception(Exception ex) {
		exception(ex, ExceptionLevel.Normal);
	}

	/**
	 * 记录异常信息
	 * 
	 * @param ex
	 * @param level
	 */
	public void exception(Exception ex, ExceptionLevel level) {
		System.out.print("----> server exception: ");
		System.out.print(DateFormatter.now().toDateTimeString());
		System.out
				.println(String.format("  (ex level: %1$s)", level.getName()));
		System.out.println(ex.getMessage());
		ex.printStackTrace();
		System.out.println();

		if (!level.equals(ExceptionLevel.CanIgnore)) {
			writeToFile(false, ex, null);
		}
	}

	private void printMsgs(boolean clientLog, String[] messages) {
		if (messages == null || messages.length == 0) {
			return;
		}

		System.out.print(String.format("----> %1$s logs: ",
				clientLog ? "client" : "server"));
		System.out.println(DateFormatter.now().toDateTimeString());
		for (String msg : messages) {
			System.out.println(msg);
		}
		System.out.println();
	}

	private void writeToFile(boolean clientLog, Exception ex, String[] messages) {
		String path = clientLog ? (logDirPath + "client/") : logDirPath;
		String fileName = path + DateFormatter.now().toDateString() + ".txt";
		FileUtil.createFolders(path);

		try {
			Writer w = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileName, true), "utf-8"));
			w.write(DateFormatter.now().toDateTimeString());
			w.write("\r\n");
			if (messages != null) {
				for (String msg : messages) {
					w.write(msg);
					w.write("\r\n");
				}
			}
			if (ex != null) {
				w.write(ex.getMessage());
				w.write("\r\n");

				String stackPath = logDirPath + "stack/"
						+ DateFormatter.now().toDateString() + "/";
				String stackFileName = DateFormatter.now()
						.toDateTimeNumberString();
				stackFileName += "-"
						+ String.valueOf(Math.abs(new Random().nextInt()))
						+ ".txt";

				FileUtil.createFolders(stackPath);
				ex.printStackTrace(new PrintStream(new FileOutputStream(
						stackPath + stackFileName), true, "utf-8"));
			}

			w.write("\r\n");
			w.close();
		} catch (Exception e) {
		}
	}
}
