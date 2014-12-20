/**
 * 
 */
package cn.mxj.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.mxj.business.WebConfig;
import cn.mxj.io.AppLogger;
import cn.mxj.string.StringUtil;

/**
 * @author fl
 * 
 */
public class FileDownloadServlet extends HttpServlet {

	private static final long serialVersionUID = -246161741501925494L;

	protected AppLogger logger = AppLogger.getInstance();

	private String uploadPath;

	/**
	 * 上传文件时使用的路径
	 * 
	 * @return example: {...Tomcat 5.5}/webapps/tester-web/data/
	 */
	public String getUploadPath() {
		return uploadPath;
	}

	@Override
	public void init() throws ServletException {
		super.init();

		synchronized (this) {
			if (uploadPath == null) {
				uploadPath = WebConfig.getInstance("").getUploadPath();
			}
		}

		System.out
				.println("INFO: --> FileDownloadServlet has been initialized. Class: "
						+ this.getClass().getName());
	}

	/**
	 * 处理下载文件的请求。需提供的参数： filePath: 相对于上传路径的相对路径，如 folder1/folder2/file1.txt，将使用
	 * WebConfig.getUploadPath() 得到的路径和此相对路径来组成完整的文件路径，然后通过此路径向客户端响应文件流。
	 * origFileName: 文件的原始名称，一般是有意义的名称，如 leo.jpg，此参数用于用户保存文件时的文件重命名。 具体目录信息请参考
	 * cn.skyclass.business.WebConfig 类。
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		ServletOutputStream out = response.getOutputStream();

		String origFileName = this.getParam(request, "origFileName");
		String filePath = this.getTargetFilePath(request);

		File file = new File(filePath);
		if (!file.exists()) {
			logger.info("文件不存在! path:" + file.getAbsolutePath());
		} else {
			this.responseFile(response, origFileName, filePath);
		}

		out.close();
	}

	/**
	 * 获取要下载的文件的全路径
	 * 
	 * @param request
	 * @return
	 */
	protected String getTargetFilePath(HttpServletRequest request) {
		String filePath = this.getParam(request, "filePath");
		return getUploadPath() + filePath;
	}

	/**
	 * 开始向客户端响应文件流
	 * 
	 * @param response
	 * @param fileName
	 *            客户端将看到的文件名
	 * @param filePath
	 *            将要响应的文件的完整路径（服务器端的）
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void responseFile(HttpServletResponse response, String fileName,
			String filePath) throws ServletException, IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}

		if (StringUtil.isNullOrEmpty(fileName)) {
			fileName = file.getName();
		}

		response.setContentType("application/x-msdownload");

		// 设置下载保存的文件名
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ fileName + "\"");

		ServletOutputStream out = response.getOutputStream();
		FileInputStream in = new FileInputStream(file);
		int readed = 0;
		byte[] buffer = new byte[1024];
		while ((readed = in.read(buffer)) != -1) {
			out.write(buffer, 0, readed);
		}
		in.close();
	}

	/**
	 * 获取经过正确编码的参数值
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	protected String getParam(HttpServletRequest request, String paramName) {
		String param = request.getParameter(paramName);
		if (StringUtil.isNullOrEmpty(param)) {
			return "";
		} else {
			return param;
			// try {
			// return new String(param.getBytes("ISO8859_1"), "utf-8");
			// } catch (Exception e) {
			// logger.exception(e);
			// return "";
			// }
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out
				.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass().getName());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
